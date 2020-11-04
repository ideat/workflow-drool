package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsCreditRequestDto;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ExceptionCreditRequestDtoController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryExceptionsCreditRequestDto repository;

    private Integer numberRequest;
    private String codeException;

    @GetMapping(value = "/v1/exceptionsCreditRequestDto/getByNumberRequest",name = "Obtiene excepciones por numero solicitud")
    ResponseEntity<Collection<ExceptionsCreditRequestDto>> getByNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        List<ExceptionsCreditRequestDto> exceptionsCreditRequestDtoList = repository.getByNumberRequest(numberRequest);
        return new ResponseEntity<>(exceptionsCreditRequestDtoList, HttpStatus.OK);
    }


    @GetMapping(value = "/v1/exceptionsCreditRequestDto/getByCodeExceptionNumberRequest",name = "Obtiene excepciones por numero solicitud")
    ResponseEntity<ExceptionsCreditRequestDto> getByCodeExceptionNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("code-exception")) codeException = value;
        });

        ExceptionsCreditRequestDto exceptionsCreditRequestDto = repository.getByCodeExceptionNumberRequest(codeException,numberRequest).get();
        return new ResponseEntity<>(exceptionsCreditRequestDto, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptionsCreditRequestDto/getAll",name = "Obtiene todas las excepciones de las solicitudes")
    ResponseEntity<Collection<ExceptionsCreditRequestDto>> getAll(){
        List<ExceptionsCreditRequestDto> exceptionsCreditRequestDtoList = repository.getAll();
        return new ResponseEntity<>(exceptionsCreditRequestDtoList,HttpStatus.OK);
    }

}
