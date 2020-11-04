package com.mindware.workflow.spring.rest.creditRequest;

import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditRequestApplicant.CreateCreditRequestApplicant;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest/", produces = {"application/json"})
public class CreditRequestApplicantController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditRequestApplicantController.class);
    private Integer numberRequest;
    private Integer numberApplicant;
    private String typeRelation;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;

    @PostMapping(value = "/v1/creditrequestapplicant/add", name = "Crear relacion")
    ResponseEntity<CreditRequestApplicant> create(@RequestBody CreditRequestApplicant creditRequestApplicant, HttpServletRequest request){
        UseCase<CreditRequestApplicant> useCase = useCaseFactory.create(CreateCreditRequestApplicant.class.getSimpleName(),creditRequestApplicant);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(creditRequestApplicant, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/v1/creditrequestapplicant/update", name = "Actualiza applicante")
    ResponseEntity<CreditRequestApplicant> updateApplicant(@RequestBody CreditRequestApplicant creditRequestApplicant){
        repositoryCreditRequestApplicant.update(creditRequestApplicant);
        return new ResponseEntity<CreditRequestApplicant>(creditRequestApplicant,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getApplicant", name = "Obtener Aplicante a credito")
    ResponseEntity<CreditRequestApplicant> getCreditRequestApplicant(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) -> {
            if(key.equals("numberrequest")) numberRequest = Integer.parseInt(value);
            if(key.equals("numberapplicant")) numberApplicant = Integer.parseInt(value);
            if(key.equals("typerelation")) typeRelation = value;
        });
        CreditRequestApplicant creditRequestApplicant = repositoryCreditRequestApplicant.getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(numberRequest, numberApplicant,typeRelation).get();
        return new ResponseEntity<>(creditRequestApplicant,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/creditrequestapplicant/delete/{id}", name = "Borrar relacion")
    ResponseEntity<String> delete(@PathVariable String id){
        repositoryCreditRequestApplicant.delete(UUID.fromString(id));
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
