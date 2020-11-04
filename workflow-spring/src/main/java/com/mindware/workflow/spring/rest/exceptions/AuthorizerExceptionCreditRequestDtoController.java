package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizerExceptionCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizerExceptionsCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class AuthorizerExceptionCreditRequestDtoController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryAuthorizerExceptionCreditRequestDto repository;

    private String city;
    private String loginUser;
    private Double minumum;
    private Double maximum;
    private String currency;
    private List<String> riskType;

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getAll", name = "Obtiene todas las solicitudes con excepciones")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getAll(){
        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getAll();
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByCity", name = "Obtiene todas las solicitudes con excepciones de una ciudad")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByCity(city);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);

    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByUser", name = "Obtiene todas las solicitudes con excepciones de una usuario")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("login-user")) loginUser = value;
        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByUser(loginUser);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);

    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByCityCurrencyAmounts", name = "Obtiene todas las solicitudes con excepciones por ciudad, moneda y rango montos")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByCityCurrencyAmounts(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
            if(key.equals("currency")) currency = value;
            if(key.equals("minimum")) minumum = Double.parseDouble(value);
            if(key.equals("maximum")) maximum = Double.parseDouble(value);
        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByCityCurrencyAmounts(city,currency,minumum,maximum);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByCurrencyAmounts", name = "Obtiene todas las solicitudes con excepciones por ciudad, moneda y rango montos")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByCurrencyAmounts(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("currency")) currency = value;
            if(key.equals("minimum")) minumum = Double.parseDouble(value);
            if(key.equals("maximum")) maximum = Double.parseDouble(value);
        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByCurrencyAmounts(currency,minumum,maximum);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByRiskType", name = "Obtiene todas las solicitudes con excepciones por tipo de riesgo")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByRiskType(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("risk-type")) riskType = Stream.of(value.split(","))
                                                    .map(String::valueOf)
                                                    .collect(Collectors.toList());

        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByRiskType(riskType);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerExceptionCreditRequest/getByRiskTypeCity", name = "Obtiene todas las solicitudes con excepciones por tipo de riesgo")
    ResponseEntity<Collection<AuthorizerExceptionsCreditRequestDto>> getByRiskTypeCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("risk-type")) riskType = Stream.of(value.split(","))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            if(key.equals("city")) city = value;
        });

        List<AuthorizerExceptionsCreditRequestDto> authorizerExceptionsCreditRequestDtos = repository.getByRiskTypeCity(riskType,city);
        return new ResponseEntity<>(authorizerExceptionsCreditRequestDtos,HttpStatus.OK);
    }
}
