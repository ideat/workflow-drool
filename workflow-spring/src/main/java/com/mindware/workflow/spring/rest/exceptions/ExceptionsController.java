package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.entity.exceptions.Exceptions;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptions;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.exceptions.CreateExceptions;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ExceptionsController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryExceptions repositoryExceptions;

    private String code;
    private UUID id;

    @PostMapping(value = "/v1/exceptions/add", name = "Crear excepcion")
    ResponseEntity<Exceptions> create(@RequestBody Exceptions exceptions, HttpServletRequest request){
        UseCase<Exceptions> useCase = useCaseFactory.create(CreateExceptions.class.getSimpleName(),exceptions);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(exceptions,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/exceptions/getAll", name = "Obtiene todas las excepciones")
    ResponseEntity<Collection<Exceptions>> getAll(){
        List<Exceptions> exceptionsList = repositoryExceptions.getAll();
        return new ResponseEntity<>(exceptionsList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptions/getById", name = "Obtiene excepcion por ID")
    ResponseEntity<Exceptions> getById(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("internal-code")) id = UUID.fromString(value);

        });

        Exceptions exceptions = repositoryExceptions.getById(id).get();
        return new ResponseEntity<>(exceptions,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/exceptions/getByInternalCode", name = "Obtiene excepcion por codigo interno")
    ResponseEntity<Exceptions> getByInternalCode(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("internal-code")) code = value;

        });

        Optional<Exceptions> exceptions = repositoryExceptions.getByInternalCode(code);
        if(exceptions.isPresent()){
            return new ResponseEntity<>(exceptions.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Exceptions(),HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "v1/exceptions/delete", name = "Borra una excepcion")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("internal-code")) id = UUID.fromString(value);

        });
        repositoryExceptions.delete(id);
        return new ResponseEntity<>("Excepcion borrada",HttpStatus.OK);
    }





}
