package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.ExchangeRate;
import com.mindware.workflow.core.service.data.config.RepositoryExchangeRate;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateExchangeRate;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ExchangeRateController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryExchangeRate repository;

    private String currency;

    @PostMapping(value = "/v1/exchangeRate/add", name = "Crea tipo de cambio")
    ResponseEntity<ExchangeRate> create(@RequestBody ExchangeRate exchangeRate, HttpServletRequest request){
        UseCase<ExchangeRate> useCase = useCaseFactory.create(CreateExchangeRate.class.getSimpleName(),exchangeRate);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(exchangeRate,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/exchangeRate/getAll", name = "Obtiene todos los tipos de cambio")
    ResponseEntity<Collection<ExchangeRate>> getAll(){
        List<ExchangeRate> exchangeRateList = repository.getAll();
        return new ResponseEntity<>(exchangeRateList,HttpStatus.OK);
    }

    @GetMapping(value ="/v1/exchangeRate/getActiveExchangeRateByCurrency", name = "Obtiene tipo cambio activo de una moneda")
    ResponseEntity<ExchangeRate> getActiveExchangeRateByCurrency(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("currency")) currency = value;
        });

        Optional<ExchangeRate> exchangeRate = repository.getActiveExchangeRateByCurrency(currency);
        if(exchangeRate.isPresent()){
            return new ResponseEntity<>(exchangeRate.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ExchangeRate(),HttpStatus.OK);
        }
    }
}
