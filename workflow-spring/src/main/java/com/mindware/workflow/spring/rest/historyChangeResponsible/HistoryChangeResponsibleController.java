package com.mindware.workflow.spring.rest.historyChangeResponsible;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.config.ProductTypeCredit;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import com.mindware.workflow.core.entity.exceptions.StatusReview;
import com.mindware.workflow.core.entity.historyChangeResponsible.HistoryChangeResponsible;
import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequest;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsible;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.historyChangeResponsible.CreateHistoryChangeResponsible;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class HistoryChangeResponsibleController {
    private static Logger LOGGER = LoggerFactory.getLogger(HistoryChangeResponsibleController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryHistoryChangeResponsible repositoryHistoryChangeResponsible;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryLegalInformation repositoryLegalInformation;

    @Autowired
    RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest;

    @Autowired
    RepositoryStageHistory repositoryStageHistory;

    private CreditRequest creditRequest;
    private List<StageHistory> stageHistoryList;

    @PostMapping(value ="/v1/historyChangeResponsible/add", name = "Crear historico de cambio de responsable")
    ResponseEntity<HistoryChangeResponsible> create(@RequestBody HistoryChangeResponsible historyChangeResponsible, HttpServletRequest request) throws IOException {
        UseCase<HistoryChangeResponsible> useCase = useCaseFactory.create(CreateHistoryChangeResponsible.class.getSimpleName(),historyChangeResponsible);
        useCase.execute();

        updateUsers(historyChangeResponsible.getNumberRequest(), historyChangeResponsible.getRolName()
                ,historyChangeResponsible.getNewResponsible(),historyChangeResponsible.getOldResponsible());

        return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
    }

    private void updateUsers(Integer numberRequest, String rolName, String newUser, String currentUser) throws IOException {
        if(rolName.equals("OFICIAL")){
            creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
            creditRequest.setLoginUser(newUser);
            repositoryCreditRequest.updateUser(creditRequest);

            updateWorkflow(numberRequest, newUser, currentUser);

        }else if(rolName.contains("LEGAL")){
            LegalInformation legalInformation = repositoryLegalInformation.getByNumberRequest(numberRequest).get();
            legalInformation.setCreatedBy(newUser);
            repositoryLegalInformation.updateUser(legalInformation);

            updateWorkflow(numberRequest, newUser, currentUser);
        }else if(rolName.contains("APROBACION")){
            List<ExceptionsCreditRequest> exceptionsCreditRequestList = repositoryExceptionsCreditRequest.getByNumberRequest(numberRequest);
            ObjectMapper mapper = new ObjectMapper();
            for(ExceptionsCreditRequest ex:exceptionsCreditRequestList){
                List<StatusReview> statusReviewList = mapper.readValue(ex.getStatusReview(), new TypeReference<List<StatusReview>>() { });
                statusReviewList = statusReviewList.stream()
                        .filter(s -> s.getLoginUser().equals(currentUser))
                        .collect(Collectors.toList());
                List<StatusReview> newStatusReviewList = new ArrayList<>();
                for(StatusReview sr:statusReviewList){
                    sr.setLoginUser(newUser);
                    newStatusReviewList.add(sr);
                }

                String jsonStr = mapper.writeValueAsString(newStatusReviewList);
                ex.setStatusReview(jsonStr);
                repositoryExceptionsCreditRequest.updateUser(ex);
                updateWorkflow(numberRequest, newUser, currentUser);
            }
        }else{
            updateWorkflow(numberRequest, newUser, currentUser);
        }

    }

    private void updateWorkflow(Integer numberRequest, String newUser, String currentUser) {
        stageHistoryList = repositoryStageHistory.getByNumberRequest(numberRequest);
        stageHistoryList = stageHistoryList.stream()
                .filter(s -> s.getUserTask().equals(currentUser))
                .collect(Collectors.toList());
        for(StageHistory sh:stageHistoryList){
            sh.setUserTask(newUser);
            repositoryStageHistory.updateUser(sh);
        }
    }
}
