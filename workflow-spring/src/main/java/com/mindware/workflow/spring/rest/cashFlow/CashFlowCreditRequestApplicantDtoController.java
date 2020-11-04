package com.mindware.workflow.spring.rest.cashFlow;

import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlowCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestApplicantDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.apache.ibatis.annotations.Param;
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
public class CashFlowCreditRequestApplicantDtoController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCashFlowCreditRequestApplicantDto repository;

    @GetMapping(value = "/v1/cashFlowCreditRequestApplicant/getAll")
    ResponseEntity<Collection<CashFlowCreditRequestApplicantDto>> getAll(){
        List<CashFlowCreditRequestApplicantDto> cashFlowCreditRequestApplicantDtoList = repository.getAll();
        return new ResponseEntity<>(cashFlowCreditRequestApplicantDtoList, HttpStatus.OK);
    }


    @GetMapping(value = "/v1/cashFlowCreditRequestApplicant/getByCity/{city}")
    ResponseEntity<Collection<CashFlowCreditRequestApplicantDto>> getByCity(@PathVariable("city") String city){
        List<CashFlowCreditRequestApplicantDto> cashFlowCreditRequestApplicantDtoList = repository.getByCity(city);
        return new ResponseEntity<>(cashFlowCreditRequestApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/cashFlowCreditRequestApplicant/getByLogin/{login}")
    ResponseEntity<Collection<CashFlowCreditRequestApplicantDto>> getByLogin(@PathVariable("login") String login){
        List<CashFlowCreditRequestApplicantDto> cashFlowCreditRequestApplicantDtoList = repository.getByLoginUser(login);
        return new ResponseEntity<>(cashFlowCreditRequestApplicantDtoList, HttpStatus.OK);
    }
}
