package com.mindware.workflow.spring.rest.contract;

import com.mindware.workflow.core.entity.contract.ContractVariable;
import com.mindware.workflow.core.service.data.legal.RepositoryContractVariable;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.legal.CreateContractVariable;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ContractVariableController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryContractVariable repository;

    private String name;
    private UUID id;

    @PostMapping(value = "/v1/contractVariable/add", name = "Crea una variable de contrato")
    ResponseEntity<ContractVariable> create(@RequestBody ContractVariable contractVariable, HttpServletRequest request){
        UseCase<ContractVariable> useCase = useCaseFactory.create(CreateContractVariable.class.getSimpleName(),contractVariable);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(contractVariable,HttpStatus.OK);
        }

    }

    @GetMapping(value = "/v1/contractVariable/getAll", name = "Obtiene todas las variable")
    ResponseEntity<Collection<ContractVariable>> getAll(){
        List<ContractVariable> contractVariableList = repository.getAll();
        return new ResponseEntity<>(contractVariableList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/contractVariable/getByName", name = "Obtiene variable por su nombre")
    ResponseEntity<ContractVariable> getByName(@RequestHeader Map<String,String> header){
        header.forEach((key,value) ->{
            if(key.equals("name")) name = value;
        });

        Optional<ContractVariable> contractVariable = repository.getByName(name);
        if(contractVariable.isPresent()){
            return new ResponseEntity<>(contractVariable.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ContractVariable(),HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "v1/contractVariable/delete", name = "Borra una variable de contrato por Id")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("id")) id = UUID.fromString(value);
        });

        repository.delete(id);
        return new ResponseEntity<>("Variable contrato borrada",HttpStatus.OK);
    }
}
