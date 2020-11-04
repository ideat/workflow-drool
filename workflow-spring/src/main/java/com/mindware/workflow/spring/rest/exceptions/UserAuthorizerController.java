package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryUserAuthorizer;
import com.mindware.workflow.core.service.data.exceptions.dto.UserAuthorizer;
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
public class UserAuthorizerController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryUserAuthorizer repository;

    private String city;

    @GetMapping(value = "/v1/userAuthorizer/getAll", name = "Obtiene todos los usuarios autorizadores")
    ResponseEntity<Collection<UserAuthorizer>> getAll(){
        List<UserAuthorizer> userAuthorizerList = repository.getAll();
        return new ResponseEntity<>(userAuthorizerList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/userAuthorizer/getByCity", name = "Obtiene usuarios autorizadores de una ciudad")
    ResponseEntity<Collection<UserAuthorizer>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;

        });

        List<UserAuthorizer> userAuthorizerList = repository.getByCity(city);
        return new ResponseEntity<>(userAuthorizerList,HttpStatus.OK);
    }
}
