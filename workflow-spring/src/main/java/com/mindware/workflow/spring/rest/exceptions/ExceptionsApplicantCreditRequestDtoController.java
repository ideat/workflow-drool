package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsApplicantCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsApplicantCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ExceptionsApplicantCreditRequestDtoController {
    private String city;
    private String user;
    @Autowired
    RepositoryExceptionsApplicantCreditRequestDto repository;

    @GetMapping(value = "/v1/exceptions-applicant-creditrequest/getAll", name = "Obtiene toda la lista excepciones pendientes")
    ResponseEntity<Collection<ExceptionsApplicantCreditRequestDto>> getAll(){
        List<ExceptionsApplicantCreditRequestDto> exceptionsApplicantCreditRequestDtoList = repository.getAll();
        return new ResponseEntity<>(exceptionsApplicantCreditRequestDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptions-applicant-creditrequest/getByUser", name ="Obtiene lista excepciones pendientes registradas por el usuario" )
    ResponseEntity<Collection<ExceptionsApplicantCreditRequestDto>> getByUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("user")) user = value;
        });

        List<ExceptionsApplicantCreditRequestDto> exceptionsApplicantCreditRequestDtoList = repository.getByUser(user);

        return new ResponseEntity<>(exceptionsApplicantCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptions-applicant-creditrequest/getByCity", name ="Obtiene lista excepciones pendientes de una ciudad" )
    ResponseEntity<Collection<ExceptionsApplicantCreditRequestDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("city")) city = value;
        });

        List<ExceptionsApplicantCreditRequestDto> exceptionsApplicantCreditRequestDtoList = repository.getByCity(city);

        return new ResponseEntity<>(exceptionsApplicantCreditRequestDtoList,HttpStatus.OK);
    }
}
