package com.mindware.workflow.spring.rest.observartionCreditRequestApplicant;

import com.mindware.workflow.core.service.data.observation.dto.ObservationCreditRequestApplicant;
import com.mindware.workflow.core.service.data.observation.dto.RepositoryObservationCreditRequestApplicant;
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
@RequestMapping(value = "/rest", produces = "application/json")
public class ObservationCreditRequestApplicantController {
    private static Logger LOGGER = LoggerFactory.getLogger(ObservationCreditRequestApplicantController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryObservationCreditRequestApplicant repository;

    @GetMapping(value = "/v1/observationCreditRequestApplicant/getByCity/{city}", name = "Lista solicitudes y observaciones por ciudad")
    ResponseEntity<Collection<ObservationCreditRequestApplicant>> getByCity(@PathVariable("city") String city){
        List<ObservationCreditRequestApplicant> observationCreditRequestApplicantList = repository.getObservationCreditRequestApplicantsByCity(city);
        return new ResponseEntity<>(observationCreditRequestApplicantList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/observationCreditRequestApplicant/getByLogin/{login}", name = "Lista solicitudes y observaciones por login usuario")
    ResponseEntity<Collection<ObservationCreditRequestApplicant>> getByLUser(@PathVariable("login") String login){
        List<ObservationCreditRequestApplicant> observationCreditRequestApplicantList = repository.getObservationCreditRequestApplicantsByUser(login);
        return new ResponseEntity<>(observationCreditRequestApplicantList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/observationCreditRequestApplicant/getAll", name = "Lista solicitudes y observaciones ")
    ResponseEntity<Collection<ObservationCreditRequestApplicant>> getAll(){
        List<ObservationCreditRequestApplicant> observationCreditRequestApplicantList = repository.getAll();
        return new ResponseEntity<>(observationCreditRequestApplicantList, HttpStatus.OK);
    }
}
