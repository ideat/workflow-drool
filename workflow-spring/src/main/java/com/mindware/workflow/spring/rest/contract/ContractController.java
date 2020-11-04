package com.mindware.workflow.spring.rest.contract;

import com.mindware.workflow.core.entity.contract.Contract;
import com.mindware.workflow.core.service.data.contract.RepositoryContract;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.contract.CreateContract;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.WordReplaceTextContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ContractController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryContract repository;

    @Autowired
    WordReplaceTextContract wordReplaceTextContract;

    @Value("${path_contract}")
    private String pathContract;

    private UUID id;
    private Integer numberRequest;

    @PostMapping(value = "/v1/contract/add",name = "Crea un contrato")
    ResponseEntity<Contract>  create(@RequestBody Contract contract, HttpServletRequest request) throws IOException {

        contract.setPathContract(pathContract+ File.separator+contract.getNumberRequest().toString()+".docx");

        UseCase<Contract> useCase = useCaseFactory.create(CreateContract.class.getSimpleName(),contract);
        useCase.execute();
//        wordReplaceTextContract = new WordReplaceTextContract();
        wordReplaceTextContract.generateContract(contract);
        if(useCase.getResult().isPresent()){

            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(contract,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/contract/delete",name = "Borra un contrato")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("id")) id = UUID.fromString(value);
        });

        repository.delete(id);

        return new ResponseEntity<>("Contrato eliminado",HttpStatus.OK);
    }

    @GetMapping(value = "/v1/contract/getByNumberRequest", name = "Obtiene contrato por nro solicitud")
    ResponseEntity<Contract> getByNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        Optional<Contract> contract = repository.getByNumberRequest(numberRequest);
        if(contract.isPresent()){
            return new ResponseEntity<>(contract.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Contract(),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/contracdt/getById", name = "Obtiene contrato por ID")
    ResponseEntity<Contract> getById(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("id")) id = UUID.fromString(value);
        });

        return new ResponseEntity<>(repository.getById(id).get(),HttpStatus.OK);
    }

}
