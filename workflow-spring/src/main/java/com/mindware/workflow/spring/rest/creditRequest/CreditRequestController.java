package com.mindware.workflow.spring.rest.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditRequest.CreateCreditRequest;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditRequestController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditRequestController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @PostMapping(value = "/v1/creditrequest/add", name = "Crear solicitud")
    ResponseEntity<CreditRequest> create(@RequestBody CreditRequest creditRequest, HttpServletRequest request){
        UseCase<CreditRequest> useCase = useCaseFactory.create(CreateCreditRequest.class.getSimpleName(),creditRequest);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(creditRequest, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/creditrequest/getAll", name = "Obtener listado solicitudes")
    ResponseEntity<Collection<CreditRequest>> getAllCreditRequest(){
        List<CreditRequest> creditRequests = repositoryCreditRequest.getAllCreditRequest();
        return new ResponseEntity<>(creditRequests,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequest/getById/{id}", name = "Obtener por ID")
    ResponseEntity<CreditRequest> getById(@PathVariable("id")UUID id){
        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestById(id).get();
        return new ResponseEntity<>(creditRequest,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequest/getByNumberRequest/{numberRequest}", name = "Obtener por Numero solicitud")
    ResponseEntity<CreditRequest> getByNumberRequest(@PathVariable("numberRequest") Integer numberRequest){
        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        return new ResponseEntity<>(creditRequest,HttpStatus.OK);
    }

    @PutMapping(value = "/v1/creditrequest/updateCompanySizeIndicator", name = "Actualiza datos size company")
    ResponseEntity<CreditRequest> updateCompanySizeIndicator(@RequestBody CreditRequest creditRequest){
        repositoryCreditRequest.updateCompanySizeIndicator(creditRequest);
        return new ResponseEntity<>(creditRequest,HttpStatus.OK);
    }

}
