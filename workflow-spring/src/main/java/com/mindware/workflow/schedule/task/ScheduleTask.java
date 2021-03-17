package com.mindware.workflow.schedule.task;

import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.email.Mail;
import com.mindware.workflow.core.entity.exceptions.Authorizer;
import com.mindware.workflow.core.entity.exceptions.StatusReview;
import com.mindware.workflow.core.service.data.RepositoryException;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditResolution.dto.Exceptions;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizer;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptions;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsApplicantCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequest;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsApplicantCreditRequestDto;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class ScheduleTask {
    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest;

    @Autowired
    RepositoryExceptions repositoryExceptions;

    @Autowired
    RepositoryExceptionsApplicantCreditRequestDto repositoryExceptionsApplicantCreditRequestDto;

    @Autowired
    RepositoryAuthorizer repositoryAuthorizer;

    @Autowired
    MailService mailService;

    @Autowired
    RepositoryMail repositoryMail;

    @Autowired
    RepositoryParameter repositoryParameter;

    private StatusReview statusReview;
    private List<ExceptionsApplicantCreditRequestDto> exceptionsList;
    private List<Users> usersList;

    @Value("${spring.mail.username}")
    private String userName;

    public void expirationUserPassword(){
        usersList = repositoryUsers.getAllUsers();
        usersList = usersList.stream().filter(u->u.getState().equals("ACTIVO"))
                .collect(Collectors.toList());
        int days=0;
        for(Users u:usersList){
            days = calculateDays(u,LocalDate.now());
            if(days>u.getNumDaysValidity()){
                u.setState("RESET");
                repositoryUsers.updateUser(u);
            }
        }
    }

    public void notificationExceptions(){
        List<Parameter> parameterList = repositoryParameter.getParametersByCategory("NOTIFICACION EXCEPCIONES");
        List<ExceptionsApplicantCreditRequestDto> exceptionsApplicantCreditRequestDtoList = new ArrayList<>();
        exceptionsList = repositoryExceptionsApplicantCreditRequestDto.getAll();

        for(Parameter p:parameterList){
            List<ExceptionsApplicantCreditRequestDto> listExceptions= exceptionsList.stream()
                    .filter(e -> e.getRemainingDays().equals(Integer.valueOf(p.getValue())))
                    .collect(Collectors.toList());
            exceptionsApplicantCreditRequestDtoList.addAll(listExceptions);
        }

        List<Mail> mailList = getListMailToSend(exceptionsApplicantCreditRequestDtoList);

        ExecutorService executor = null;
        try {
           executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                for (Mail m : mailList) {
                    repositoryMail.add(m);
                    mailService.sendMail(m);
                }
            });
        }finally {
            executor.shutdown();
        }

    }

    private int calculateDays(Users users, LocalDate actualDate){
        Long days=0l;

        if (users.getDateUpdatePassword() != null) {
            days = DAYS.between(users.getDateUpdatePassword(),actualDate);
        }else{
            days = DAYS.between(users.getDateCreation(),actualDate);
        }

        return days.intValue();
    }

    private List<Mail> getListMailToSend(List<ExceptionsApplicantCreditRequestDto> exceptionsList){
        List<Mail> mailList = new ArrayList<>();
        for(ExceptionsApplicantCreditRequestDto ex : exceptionsList){
            List<Authorizer> authorizerList = repositoryAuthorizer.getByRiskType(ex.getRiskType());
            for(Authorizer a:authorizerList){
                Users users = repositoryUsers.getUserByIdUser(a.getLoginUser()).get();
                Mail mail = generateMail(users.getEmail(),"Mail automatic",ex.getNumberRequest()
                        ,ex.getCodeException(),ex.getRemainingDays());
                mailList.add(mail);
            }
        }
        return mailList;
    }

    private Mail generateMail(String email, String loginUser, Integer numberRquest,
                              String exceptionCode, Integer remainingDays){
        Mail mail = new Mail();
        mail.setId(UUID.randomUUID());
        mail.setLoginUser(loginUser);
        mail.setNumberRequest(numberRquest);
        mail.setSendDate(LocalDateTime.now());
        mail.setMailFrom(userName);
        mail.setMailTo(email);
        mail.setMailSubject("Notificacion proximo vencimiento plazo excepcion");
        mail.setMailContent(String.format("Excepcion %s de la solicitud %s vence su plazo de regularizacion en %s dias"
        , exceptionCode,numberRquest,remainingDays));
        return mail;
    }



}
