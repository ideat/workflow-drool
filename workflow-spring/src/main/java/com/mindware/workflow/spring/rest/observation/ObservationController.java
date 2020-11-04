package com.mindware.workflow.spring.rest.observation;

import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.service.data.observation.RepositoryObservation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.observation.CreateObservation;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ObservationController {
    private static Logger LOGGER = LoggerFactory.getLogger(ObservationController.class);

    private Integer numberRequest;
    private Integer numberApplicant;
    private String task;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryObservation repository;

    @PostMapping(value = "v1/observation/add", name = "Crear Observacion")
    ResponseEntity<Observation> create(@RequestBody Observation observation, HttpServletRequest request){
        UseCase<Observation> useCase = useCaseFactory.create(CreateObservation.class.getSimpleName(),observation);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(observation, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/observation/getAll", name = "Obtener observaciones")
    ResponseEntity<Collection<Observation>> getall(){
        List<Observation> observationList = repository.getAll();
        return new ResponseEntity<>(observationList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/observation/getByNumberRequest/{number_request}", name = "Obtener observaciones por Numero solicitud")
    ResponseEntity<Observation> getByNumberRequest(@PathVariable("number_request") Integer numberRequest){
        Observation observation = repository.getByNumberRequest(numberRequest).get();
        return new ResponseEntity<>(observation,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/observation/getById/{id}", name = "Obtener observaciones por ID")
    ResponseEntity<Observation> getById(@PathVariable("id") UUID id){
        Observation observation = repository.getById(id).get();
        return new ResponseEntity<>(observation,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/observation/getByNumberRequestApplicantTask", name = "Obtener observaciones por Solicitud, solicitante y tarea")
    ResponseEntity<Observation> getByNumberRequestApplicantTask(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number_request")) numberRequest = Integer.parseInt(value);
            if(key.equals("task")) task = value;
        });
        Optional<Observation> observation = repository.getByNumberRequestApplicantTask(numberRequest,task);
        if(observation.isPresent())
            return new ResponseEntity<>(observation.get(),HttpStatus.OK);
        else
            return new ResponseEntity<>(new Observation(),HttpStatus.OK);
    }

}
