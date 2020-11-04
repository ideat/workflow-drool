package com.mindware.workflow.spring.rest.legal;

import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.legal.CreateLegalInformation;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class LegalInformationController {
    private static Logger LOGGER = LoggerFactory.getLogger(LegalInformationController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryLegalInformation repositoryLegalInformation;

    @PostMapping(value = "/v1/legalInformation/add", name = "Crear informe legal")
    ResponseEntity<LegalInformation> create(@RequestBody LegalInformation legalInformation, HttpServletRequest request){
        UseCase<LegalInformation> useCase = useCaseFactory.create(CreateLegalInformation.class.getSimpleName(),legalInformation);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(legalInformation,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/legalInformation/getAll", name = "Obtiene todos los informes legales")
    ResponseEntity<Collection<LegalInformation>> getAll(){
        List<LegalInformation> legalInformationList = repositoryLegalInformation.getAll();
        return new ResponseEntity<>(legalInformationList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/legalInformation/getById/{id}", name = "Obtiene informe por ID")
    ResponseEntity<LegalInformation> getById(@PathVariable("id")UUID id){
        LegalInformation legalInformation = repositoryLegalInformation.getById(id).get();
        return new ResponseEntity<>(legalInformation,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/legalInformation/getByNumberRequest/{number-request}", name = "Obtiene informe por Numero Solicitud")
    ResponseEntity<LegalInformation> getByNumberRequest(@PathVariable("number-request") Integer numberRequest){
        Optional<LegalInformation> legalInformation =  repositoryLegalInformation.getByNumberRequest(numberRequest);
        if(legalInformation.isPresent()){
            return new ResponseEntity<>(legalInformation.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new LegalInformation(),HttpStatus.OK);
        }
//        LegalInformation legalInformation = repositoryLegalInformation.getByNumberRequest(numberRequest).get();
//        return new ResponseEntity<>(legalInformation,HttpStatus.OK);
    }
}
