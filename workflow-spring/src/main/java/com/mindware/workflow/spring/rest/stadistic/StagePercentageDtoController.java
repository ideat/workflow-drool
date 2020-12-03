package com.mindware.workflow.spring.rest.stadistic;

import com.mindware.workflow.core.service.data.stadistic.RepositoryStagePercentageDto;
import com.mindware.workflow.core.service.data.stadistic.dto.StagesPercentageDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StagePercentageDtoController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryStagePercentageDto repository;

    private String yearMonth;
    private String city;
    private Integer numberPeriods;

    @GetMapping(value = "/v1/stadistic/getAllNewRequestByYearAndMonth", name = "Obtiene Nuevas Solicitudes de 1 mes")
    ResponseEntity<Collection<StagesPercentageDto>> getAllNewRequestByYearAndMonth(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value)->{
            if(key.equals("year-month")) yearMonth = value;
        });

        List<StagesPercentageDto> stagesPercentageDto = repository.getAllNewRequestByYearAndMonth(yearMonth);
        return new ResponseEntity<>(stagesPercentageDto, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getListGroupRequestDate", name = "Obtiene Lista de Solicitudes en N periodos")
    ResponseEntity<Collection<StagesPercentageDto>> getListGroupRequestDate(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> stagesPercentageDto = repository.getListGroupRequestDate();
        return new ResponseEntity<>(stagesPercentageDto, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getStageActiveCreditRequest", name = "Obtiene Lista de Solicitudes activas agrupadas por su etapa")
    ResponseEntity<Collection<StagesPercentageDto>> getStageActiveCreditRequest(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> activeCreditRequestList = repository.getActiveCreditRequest();

        return new ResponseEntity<>(activeCreditRequestList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getApprovedCreditRequest", name = "Obtiene Lista de Solicitudes desembolsadas")
    ResponseEntity<Collection<StagesPercentageDto>> getApprovedCreditRequest(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> activeCreditRequestList = repository.getApprovedCreditRequest();

        return new ResponseEntity<>(activeCreditRequestList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getRejectCreditRequest", name = "Obtiene Lista de Solicitudes rechazadas")
    ResponseEntity<Collection<StagesPercentageDto>> getRejectCreditRequest(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> activeCreditRequestList = repository.getRejectCreditRequest();

        return new ResponseEntity<>(activeCreditRequestList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getCreditRequestGroupedByCity", name = "Obtiene Lista de Solicitudes rechazadas")
    ResponseEntity<Collection<StagesPercentageDto>> getCreditRequestGroupedByCity(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> activeCreditRequestList = repository.getCreditRequestGroupedByCity();

        return new ResponseEntity<>(activeCreditRequestList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stadistic/getCreditRequestGroupedByTypeCredit", name = "Obtiene Lista de Solicitudes por Tipo de Credito")
    ResponseEntity<Collection<StagesPercentageDto>> getCreditRequestGroupedByTypeCredit(@RequestHeader Map<String,String> headers){

        List<StagesPercentageDto> activeCreditRequestList = repository.getCreditRequestGroupedByTypeCredit();

        return new ResponseEntity<>(activeCreditRequestList, HttpStatus.OK);
    }
}
