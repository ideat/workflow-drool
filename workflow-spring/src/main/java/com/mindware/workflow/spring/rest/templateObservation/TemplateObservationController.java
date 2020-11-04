package com.mindware.workflow.spring.rest.templateObservation;

import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import com.mindware.workflow.core.service.data.templateObservation.RepositoryTemplateObservation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.templateObservation.CreateTemplateObservation;
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
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class TemplateObservationController {
    private static Logger LOGGER = LoggerFactory.getLogger(TemplateObservationController.class);
    private String task;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryTemplateObservation repositoryTemplateObservation;

    @PostMapping(value = "/v1/templateObservation/add", name ="Crear Plantilla Observaciones")
    ResponseEntity<TemplateObservation> create(@RequestBody TemplateObservation templateObservation, HttpServletRequest request){
        UseCase<TemplateObservation> useCase = useCaseFactory.create(CreateTemplateObservation.class.getSimpleName(),templateObservation);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(templateObservation, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/templateObservation/getByTask/{task}")
    ResponseEntity<Collection<TemplateObservation>> getTemplateObservationByTask(@PathVariable("task") String task){

        List<TemplateObservation> templateObservationList = repositoryTemplateObservation.templateObservationByTask(task);
        return new ResponseEntity<>(templateObservationList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/templateObservation/getAll")
    ResponseEntity<Collection<TemplateObservation>> getAll(){
        List<TemplateObservation> templateObservationList = repositoryTemplateObservation.getAll();
        return new ResponseEntity<>(templateObservationList,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/templateObservation/delete/{id}", name = "Borrar")
    ResponseEntity<String> delete(@PathVariable String id){
        repositoryTemplateObservation.delete(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
