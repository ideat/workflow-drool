package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizerOfficeUserDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizersOfficeUserDto;
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

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class AuthorizerOfficeUserDtoController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryAuthorizerOfficeUserDto repository;

    private String city;
    private Double maximumAmount;
    private Double minimumAmount;
    private String scope;

    @GetMapping(value = "/v1/authorizerOfficeUser/getAll", name = "Obtiene todos lo autorizadores")
    ResponseEntity<Collection<AuthorizersOfficeUserDto>> getAll(){
        List<AuthorizersOfficeUserDto> authorizerOfficeUserDtoControllerList = repository.getAll();
        return new ResponseEntity<>(authorizerOfficeUserDtoControllerList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerOfficeUser/getByCity", name = "Obtiene autorizadores por ciudad")
    ResponseEntity<Collection<AuthorizersOfficeUserDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("city")) city = value;
        });

        List<AuthorizersOfficeUserDto> authorizersOfficeUserDtos = repository.getByCity(city);
        return new ResponseEntity<>(authorizersOfficeUserDtos,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerOfficeUser/getByAmountBs", name = "Obtiene autorizadores en Bs ")
    ResponseEntity<Collection<AuthorizersOfficeUserDto>> getByAmountBs(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("maximum-amount-bs")) maximumAmount = Double.parseDouble(value);
            if(key.equals("minimum-amount-bs")) minimumAmount = Double.parseDouble(value);
        });

        List<AuthorizersOfficeUserDto> authorizersOfficeUserDtos = repository.getByAmountBs(minimumAmount,maximumAmount);
        return new ResponseEntity<>(authorizersOfficeUserDtos,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizerOfficeUser/getByAmountSus", name = "Obtiene autorizadores en Sus ")
    ResponseEntity<Collection<AuthorizersOfficeUserDto>> getByAmountSus(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("maximum-amount-sus")) maximumAmount = Double.parseDouble(value);
            if(key.equals("minimum-amount-sus")) minimumAmount = Double.parseDouble(value);
        });

        List<AuthorizersOfficeUserDto> authorizersOfficeUserDtos = repository.getByAmountSus(minimumAmount,maximumAmount);
        return new ResponseEntity<>(authorizersOfficeUserDtos,HttpStatus.OK);
    }
}
