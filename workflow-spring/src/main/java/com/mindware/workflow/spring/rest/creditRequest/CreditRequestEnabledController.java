package com.mindware.workflow.spring.rest.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequestEnabled;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabled;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditRequest.CreateCreditRequestEnabled;
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
import java.util.Optional;

@RestController
@RequestMapping(value="/rest", produces={"application/json"})
public class CreditRequestEnabledController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditRequestEnabledController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequestEnabled repository;

    @PostMapping(value = "/v1/creditrequest-enabled/add",name = "Crear  habilitacion de una solicitud")
    ResponseEntity<CreditRequestEnabled> create(@RequestBody CreditRequestEnabled creditRequestEnabled, HttpServletRequest request){
        UseCase<CreditRequestEnabled> useCase = useCaseFactory.create(CreateCreditRequestEnabled.class.getSimpleName(),creditRequestEnabled);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(creditRequestEnabled, HttpStatus.OK);
        }
    }

    @GetMapping(value="/v1/creditrequest_enabled/getByNumberRequestOpen/{numberRequest}", name="Obtiene solicitud abierta")
    ResponseEntity<CreditRequestEnabled> getByNumberRequestOpen(@PathVariable("numberRequest") Integer numberRequest){
        Optional<CreditRequestEnabled> creditRequestEnabled = repository.getByNumberRequestOpen(numberRequest);
        if(creditRequestEnabled.isPresent()){
            return new ResponseEntity<>(creditRequestEnabled.get(),HttpStatus.OK);
        }else{
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }

    @GetMapping(value="/v1/creditrequest_enabled/getAll", name="Obtiene todas las solicitudes abiertas")
    ResponseEntity<Collection<CreditRequestEnabled>> getAll(){
        List<CreditRequestEnabled> creditRequestEnabledList = repository.getAll();
        return new ResponseEntity<>(creditRequestEnabledList,HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled/getByOffice/{codeOffice}", name="Obtiene todas las solicitudes abiertas")
    ResponseEntity<Collection<CreditRequestEnabled>> getByOffice(@PathVariable("codeOffice") Integer codeOffice){
        List<CreditRequestEnabled> creditRequestEnabledList = repository.getByOffice(codeOffice);
        return new ResponseEntity<>(creditRequestEnabledList,HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled/getByCity/{city}", name="Obtiene todas las solicitudes abiertas")
    ResponseEntity<Collection<CreditRequestEnabled>> getByCity(@PathVariable("city") String city){
        List<CreditRequestEnabled> creditRequestEnabledList = repository.getByCity(city);
        return new ResponseEntity<>(creditRequestEnabledList,HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled/getAllOpen", name="Obtiene todas las solicitudes abiertas")
    ResponseEntity<Collection<CreditRequestEnabled>> getAllOpen(){
        List<CreditRequestEnabled> creditRequestEnabledList = repository.getAllOpen();
        return new ResponseEntity<>(creditRequestEnabledList,HttpStatus.OK);
    }
}
