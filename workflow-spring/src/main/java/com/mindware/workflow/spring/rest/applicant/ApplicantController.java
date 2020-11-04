package com.mindware.workflow.spring.rest.applicant;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.applicant.CreateApplicant;
import com.mindware.workflow.exception.RestExceptionHandler;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@PreAuthorize("authenticated")
@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ApplicantController {
    private static Logger LOGGER  = LoggerFactory.getLogger(ApplicantController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @PostMapping(value = "/v1/applicant/add", name = "Crear solicitante")
    ResponseEntity<Applicant> create (@RequestBody Applicant applicant, HttpServletRequest request) {

        UseCase<Applicant> useCase = useCaseFactory.create(CreateApplicant.class.getSimpleName(),applicant);
        useCase.execute();

        if (useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(applicant,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/applicant/getAll", name= "Obtener listado solicitantes")
    ResponseEntity<Collection<Applicant>> getAllApplicant(){
        List<Applicant> applicants = repositoryApplicant.getAllApplicant();
        return new ResponseEntity<>(applicants,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/applicant/getById/{id}", name = "Obtener solicitante por ID")
    ResponseEntity<Applicant> getApplicantById(@PathVariable("id")UUID id){
        Applicant applicant = repositoryApplicant.getApplicantById(id).get();
        return new ResponseEntity<>(applicant,HttpStatus.OK);
    }

    @PutMapping(value = "/v1/applicant/update", name = "Actualizar solicitante")
    ResponseEntity<Applicant> putApplicant(@RequestBody Applicant applicant, HttpServletRequest request){
        repositoryApplicant.updateApplicant(applicant);
        return new ResponseEntity<>(applicant,HttpStatus.OK);
    }


}
