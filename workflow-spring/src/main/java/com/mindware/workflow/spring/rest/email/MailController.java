package com.mindware.workflow.spring.rest.email;

import com.mindware.workflow.core.entity.email.Mail;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.email.CreateMail;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class MailController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryMail repository;

    @Autowired
    MailService mailService;

    @PostMapping(value = "/mail/v1/add", name = "Crea mail")
    ResponseEntity<Mail> create(@RequestBody Mail mail, HttpServletRequest request){
        UseCase<Mail> useCase = useCaseFactory.create(CreateMail.class.getSimpleName(),mail);
        useCase.execute();
        Thread tmail = new Thread(new Runnable() {
            @Override
            public void run() {
                mailService.sendMail(useCase.getResult().get());
            }
        });
        tmail.start();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(mail,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/mail/v1/getByNumberRequest", name = "Obtiene mail por numero de solicitu")
    ResponseEntity<Collection<Mail>> getByNumberRequest(@PathVariable("number_request") Integer numberRequest){
        List<Mail> mailList = repository.getByNumberRequest(numberRequest);
        return new ResponseEntity<>(mailList,HttpStatus.OK);
    }

    @GetMapping(value = "/mail/v1/getById", name = "Obtiene Mail por Id")
    ResponseEntity<Mail> getById(@PathVariable("id")UUID id){
        Mail mail = repository.getById(id).get();
        return new ResponseEntity<>(mail,HttpStatus.OK);
    }

    @PutMapping(value = "/mail/v1/update", name = "Actualizar Mail")
    ResponseEntity<Mail> update(@RequestBody Mail mail, HttpServletRequest request){
        repository.update(mail);
        return new ResponseEntity<>(mail,HttpStatus.OK);
    }
}
