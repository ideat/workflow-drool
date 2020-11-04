package com.mindware.workflow.spring.rest.creditResolution;

import com.mindware.workflow.core.service.data.creditResolution.dto.CreditResolutionCreditRequestDto;
import com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto;
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

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditResolutionCreditRequestDtoController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditResolutionCreditRequestDtoController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditResolutionCreditRequestDto repository;

    @GetMapping(value = "/v1/creditResolutionCreditRequest/getByLogin/{login}", name = "Creditos a crear una Resolucion")
    ResponseEntity<List<CreditResolutionCreditRequestDto>> getByLogin(@PathVariable("login") String login){
        List<CreditResolutionCreditRequestDto> creditResolutionCreditRequestDtos = repository.getByLogin(login);
        return new ResponseEntity<>(creditResolutionCreditRequestDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditResolutionCreditRequest/getByCity/{city}", name = "Creditos a crear una Resolucion")
    ResponseEntity<List<CreditResolutionCreditRequestDto>> getByCity(@PathVariable("city") String city){
        List<CreditResolutionCreditRequestDto> creditResolutionCreditRequestDtos = repository.getByCity(city);
        return new ResponseEntity<>(creditResolutionCreditRequestDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditResolutionCreditRequest/getAll", name = "Creditos a crear una Resolucion")
    ResponseEntity<List<CreditResolutionCreditRequestDto>> getAll(){
        List<CreditResolutionCreditRequestDto> creditResolutionCreditRequestDtos = repository.getAll();
        return new ResponseEntity<>(creditResolutionCreditRequestDtos, HttpStatus.OK);
    }
}
