package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.Caedec;
import com.mindware.workflow.core.service.data.config.RepositoryCaedec;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateCaedec;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CaedecController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCaedec repositoryCaedec;

    @PostMapping(value = "/caedec/v1/add", name = "Crear CAEDEC")
    ResponseEntity<Caedec> create (@RequestBody Caedec caedec, HttpServletRequest request){
        UseCase<Caedec> useCase = useCaseFactory.create(CreateCaedec.class.getSimpleName(),caedec);
        useCase.execute();
        if (useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(caedec,HttpStatus.OK);
        }

    }


}
