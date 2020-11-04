package com.mindware.workflow.spring.rest.legal;

import com.mindware.workflow.core.service.data.legal.dto.LegalInformationCreditRequestDto;
import com.mindware.workflow.core.service.data.legal.dto.RepositoryLegalInformationCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class LegalInformationCreditRequestDtoController {
    private static Logger LOGGER = LoggerFactory.getLogger(LegalInformationCreditRequestDtoController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryLegalInformationCreditRequestDto repositoryLegalInformationCreditRequestDto;

    @GetMapping(value = "/v1/legalInformationCreditRequest/getAll", name = "Lista de solicitudes, estado de registros")
    ResponseEntity<List<LegalInformationCreditRequestDto>> getAll(){
        List<LegalInformationCreditRequestDto> result = repositoryLegalInformationCreditRequestDto.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
