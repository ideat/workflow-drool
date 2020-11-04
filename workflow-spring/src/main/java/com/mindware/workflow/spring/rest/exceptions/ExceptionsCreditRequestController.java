package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.exceptions.CreateExceptionsCreditRequest;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ExceptionsCreditRequestController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest;

    private UUID id;
    private String codeException;
    private Integer numberRequest;

    @PostMapping(value = "/v1/exceptionsCreditRequest/add", name = "Crea exception en la solicitud")
    ResponseEntity<ExceptionsCreditRequest> create(@RequestBody ExceptionsCreditRequest exceptionsCreditRequest, HttpServletRequest request){
        UseCase<ExceptionsCreditRequest> useCase = useCaseFactory.create(CreateExceptionsCreditRequest.class.getSimpleName(),exceptionsCreditRequest);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(exceptionsCreditRequest,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/exceptionsCreditRequest/getAll", name = "Obtienes todas las exceciones de solicitudes")
    ResponseEntity<Collection<ExceptionsCreditRequest>> getAll(){
        List<ExceptionsCreditRequest> exceptionsCreditRequestList = repositoryExceptionsCreditRequest.getAll();
        return new ResponseEntity<>(exceptionsCreditRequestList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptionsCreditRequest/getById", name = "Obtiene excepcion solicitud por ID")
    ResponseEntity<ExceptionsCreditRequest> getById(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id")) id = UUID.fromString(value);

        });

        ExceptionsCreditRequest exceptionsCreditRequest = repositoryExceptionsCreditRequest.getById(id).get();
        return new ResponseEntity<>(exceptionsCreditRequest,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptionsCreditRequest/getByNumberRequest", name = "Obtienes todas las excepciones de una solicitud")
    ResponseEntity<Collection<ExceptionsCreditRequest>> getByNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);

        });
        List<ExceptionsCreditRequest> exceptionsCreditRequestList = repositoryExceptionsCreditRequest.getByNumberRequest(numberRequest);
        return new ResponseEntity<>(exceptionsCreditRequestList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptionsCreditRequest/getByCodeExceptionNumberRequest", name = "Obtienes excepcion por ID excepcion y Nro solicitud")
    ResponseEntity<ExceptionsCreditRequest> getByIdExceptionNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("code-exception")) codeException = value;

        });
        ExceptionsCreditRequest exceptionsCreditRequest = repositoryExceptionsCreditRequest.getByCodeExceptionNumberRequest(codeException,numberRequest).get();
        return new ResponseEntity<>(exceptionsCreditRequest,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/exceptionsCreditRequest/delete", name = "Borra una excepcion de la solicitud")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id")) id = UUID.fromString(value);

        });
        repositoryExceptionsCreditRequest.delete(id);
        return new ResponseEntity<>("Excepcion borrada",HttpStatus.OK);
    }

}
