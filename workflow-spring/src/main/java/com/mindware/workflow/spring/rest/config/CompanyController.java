package com.mindware.workflow.spring.rest.config;


import com.mindware.workflow.core.entity.config.Company;
import com.mindware.workflow.core.service.data.config.RepositoryCompany;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateCompany;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CompanyController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCompany repositoryCompany;

    @PostMapping(value = "/company/v1/add", name="Crear empresa")
    ResponseEntity<Company> create(@RequestBody Company company, HttpServletRequest request){
        UseCase<Company> useCase = useCaseFactory.create(CreateCompany.class.getSimpleName(), company);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(company,HttpStatus.OK);
        }
    }
}
