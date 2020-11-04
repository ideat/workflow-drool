package com.mindware.workflow.spring.rest.creditResolution;

import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditResolution.CreateCreditResolution;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditResolutionController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditResolutionController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditResolution repositoryCreditResolution;

    @PostMapping(value = "/v1/creditresolution/add", name = "Crear Resolucion de Creditos")
    ResponseEntity<CreditResolution> create(@RequestBody CreditResolution creditResolution, HttpServletRequest request){
        UseCase<CreditResolution> useCase = useCaseFactory.create(CreateCreditResolution.class.getSimpleName(),creditResolution);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(creditResolution, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/creditresolution/getById/{id}", name = "Obtener por ID")
    ResponseEntity<CreditResolution> getById(@PathVariable("id")UUID id){
        CreditResolution creditResolution = repositoryCreditResolution.getById(id).get();
        return new ResponseEntity<>(creditResolution,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditresolution/getByNumberRequest/{number-request}", name = "Obener resolucion por Numero Solicitud")
    ResponseEntity<CreditResolution> getByNumberRequest(@PathVariable("number-request") Integer numberRequest){
        CreditResolution creditResolution = new CreditResolution();
        if(repositoryCreditResolution.getByNumberRequest(numberRequest).isPresent()){
            creditResolution = repositoryCreditResolution.getByNumberRequest(numberRequest).get();
        }

        return new ResponseEntity<>(creditResolution,HttpStatus.OK);
    }


}
