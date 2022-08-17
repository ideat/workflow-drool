package com.mindware.workflow.spring.rest.creditScoring;

import com.mindware.workflow.core.service.data.creditScoring.RepositoryCreditScoringCreditRequestDto;
import com.mindware.workflow.core.service.data.creditScoring.dto.CreditScoringCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditScoringCreditRequestDtoController {

    private static Logger LOGGER = LoggerFactory.getLogger(CreditScoringCreditRequestDto.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditScoringCreditRequestDto repository;

    @GetMapping(value = "/v1/creditScoringCreditRequest/getAll")
    ResponseEntity<Collection<CreditScoringCreditRequestDto>> getAll(){
        List<CreditScoringCreditRequestDto> creditScoringCreditRequestDtoList = repository.getAll();
        return new ResponseEntity<>(creditScoringCreditRequestDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditScoringCreditRequest/getByCity/{city}")
    ResponseEntity<Collection<CreditScoringCreditRequestDto>> getByCity(@PathVariable("city") String city){
        List<CreditScoringCreditRequestDto> creditScoringCreditRequestDtoList = repository.getByCity(city);
        return new ResponseEntity<>(creditScoringCreditRequestDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditScoringCreditRequest/getByLogin/{login}")
    ResponseEntity<Collection<CreditScoringCreditRequestDto>> getByLogin(@PathVariable("login") String login){
        List<CreditScoringCreditRequestDto> creditScoringCreditRequestDtoList = repository.getByLoginUser(login);
        return new ResponseEntity<>(creditScoringCreditRequestDtoList, HttpStatus.OK);
    }
}
