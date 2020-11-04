package com.mindware.workflow.spring.rest.patrimonialStatement;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.patrimonialStatement.CreatePatrimonialStatement;
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
public class PatrimonialStatementController {
    private static Logger LOGGER = LoggerFactory.getLogger(PatrimonialStatementController.class);
    private UUID idCreditRequestApplicant;
    private String category;
    private String element;


    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryPatrimonialStatement repository;

    @PostMapping(value = "/v1/patrimonialstatement/add", name = "Crear declaracion patrimonial")
    ResponseEntity<PatrimonialStatement> create(@RequestBody PatrimonialStatement patrimonialStatement, HttpServletRequest request){
        UseCase<PatrimonialStatement> useCase = useCaseFactory.create(CreatePatrimonialStatement.class.getSimpleName(), patrimonialStatement);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return  new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(patrimonialStatement, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/patrimonialstatement/getByIdCreditRequestApplicantCategory", name = "Obtener declaracion por categoria")
    ResponseEntity<List<PatrimonialStatement>> getByIdCreditRequestApplicantCategory(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
           if (key.equals("idcreditrequestapplicant")) idCreditRequestApplicant = UUID.fromString(value);
           if (key.equals("category")) category = value;
        });

        List<PatrimonialStatement> patrimonialStatementList = repository.getByIdCreditRequestApplicantCategory(idCreditRequestApplicant,category);
        return new ResponseEntity<>(patrimonialStatementList,HttpStatus.OK);
    }



    @GetMapping(value = "/v1/patrimonialstatement/getByIdCreditRequestApplicantCategoryElement", name = "Obtener declaracion por categoria y elemento")
    ResponseEntity<List<PatrimonialStatement>> getByIdCreditRequestApplicantCategoryElement(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if (key.equals("idcreditrequestapplicant")) idCreditRequestApplicant = UUID.fromString(value);
            if (key.equals("category")) category = value;
            if (key.equals("element")) element = value;
        });

        List<PatrimonialStatement> patrimonialStatementList = repository.getByIdCreditRequestApplicantCategoryElement(idCreditRequestApplicant,category,element);
        return new ResponseEntity<>(patrimonialStatementList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/patrimonialstatement/getByIdCreditRequestApplicant", name = "Obtener declaracion por ID creditrequest applicant")
    ResponseEntity<List<PatrimonialStatement>> getByIdCreditRequestApplicant(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if (key.equals("idcreditrequestapplicant")) idCreditRequestApplicant = UUID.fromString(value);
        });

        List<PatrimonialStatement> patrimonialStatementList = repository.getByIdCreditRequestApplicant(idCreditRequestApplicant);
        return new ResponseEntity<>(patrimonialStatementList,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/patrimonialstatement/delete/{id}", name = "Borrar")
    public ResponseEntity<String> delete(@PathVariable("id") String id){

        repository.delete(UUID.fromString(id));
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
