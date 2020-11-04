package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateTypeCredit;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces =  {"application/json"})
public class TypeCreditController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryTypeCredit repository;

    @PostMapping(value = "/v1/typeCredit/add", name = "Crear Tipo de Credito")
    ResponseEntity<TypeCredit> create(@RequestBody TypeCredit typeCredit, HttpServletRequest request){
        UseCase<TypeCredit> useCase = useCaseFactory.create(CreateTypeCredit.class.getSimpleName(),typeCredit);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(typeCredit,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/typeCredit/getAll", name = "Obtiene todos los tipos de credito")
    ResponseEntity<Collection<TypeCredit>> getAll(){
        List<TypeCredit> typeCredits = repository.getAll();
        return new ResponseEntity<>(typeCredits,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/typeCredit/getByExternalCode/{code}", name = "Obtener tipo de credito por codigo")
    ResponseEntity<TypeCredit> getByExternalCode(@PathVariable("code") String code){
        TypeCredit typeCredit = new TypeCredit();
        if(repository.getByExternalCode(code).isPresent()){
            typeCredit = repository.getByExternalCode(code).get();
        }
        return new ResponseEntity<>(typeCredit,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/typeCredit/getById/{code}", name = "Obtener tipo de credito por codigo")
    ResponseEntity<TypeCredit> getById(@PathVariable("id") String id){
        TypeCredit typeCredit = new TypeCredit();
        if(repository.getById(UUID.fromString(id)).isPresent()){
            typeCredit = repository.getById(UUID.fromString(id)).get();
        }
        return new ResponseEntity<>(typeCredit,HttpStatus.OK);
    }
}
