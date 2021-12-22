package com.mindware.workflow.spring.rest.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringCreditRequest;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditScoring.CreateScoringCreditRequest;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ScoringCreditRequestController {
    private static Logger LOGGER = LoggerFactory.getLogger(ScoringCreditRequest.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryScoringCreditRequest repositoryScoringCreditRequest;

    @PostMapping(value = "/v1/scoringcreditrequest/add", name = "Crear Scoring para una solicitud de credito")
    ResponseEntity<ScoringCreditRequest> create(@RequestBody ScoringCreditRequest scoringCreditRequest, HttpServletRequest request){
        UseCase<ScoringCreditRequest> useCase = useCaseFactory.create(CreateScoringCreditRequest.class.getSimpleName(), scoringCreditRequest);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity(useCase.getResult().get(), HttpStatus.OK);
        }else{
            return new ResponseEntity(scoringCreditRequest, HttpStatus.OK);
        }

    }

    @GetMapping(value = "/v1/scoringcreditrequest/getByNumberRequest", name = "Obtiene Scoring de una solicitud de credito")
    ResponseEntity<ScoringCreditRequest> getByNumberRequest(@PathVariable("number_request") Integer numberRequest){
        Optional<ScoringCreditRequest> scoringCreditRequest = repositoryScoringCreditRequest.getByNumberRequest(numberRequest);
        if(scoringCreditRequest.isPresent()){
            return new ResponseEntity(scoringCreditRequest,HttpStatus.OK);
        }else{
            throw new UseCaseException("Scoring solicitud credito no creada");
        }

    }


}
