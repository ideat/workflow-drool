package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.entity.exceptions.Authorizer;
import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizer;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.exceptions.CreateAuthorizer;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class AuthorizerController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryAuthorizer repositoryAuthorizer;

    private String loginUser;
    private UUID id;
    private String scope;

    @PostMapping(value = "/v1/authorizer/add", name = "Crear Autorizador")
    ResponseEntity<Authorizer> create(@RequestBody Authorizer authorizer, HttpServletRequest request){
        UseCase<Authorizer> useCase = useCaseFactory.create(CreateAuthorizer.class.getSimpleName(),authorizer);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(authorizer,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/authorizer/getAll", name = "Obtiene todos los Autorizadores")
    ResponseEntity<Collection<Authorizer>> getAll(){
        List<Authorizer> authorizerList = repositoryAuthorizer.getAll();
        return new ResponseEntity<>(authorizerList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizer/getById", name = "Obtiene autorizador por ID")
    ResponseEntity<Authorizer> getById(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("id")) id = UUID.fromString(value);
        });

        Authorizer authorizer = repositoryAuthorizer.getById(id).get();
        return new ResponseEntity<>(authorizer,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/authorizer/getByLoginUser", name = "Obtiene autorizador por Login User")
    ResponseEntity<Authorizer> getByLoginUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("login-user")) loginUser = value;
        });

        Optional<Authorizer> authorizer = repositoryAuthorizer.getByLoginUsers(loginUser);
        if(authorizer.isPresent()) {
            return new ResponseEntity<>(authorizer.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Authorizer(),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/authorizer/getByScope", name = "Obtiene autorizador por su alcance")
    ResponseEntity<Collection<Authorizer>> getByScope(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("scope")) scope = value;
        });

        List<Authorizer> authorizerList = repositoryAuthorizer.getByScope(scope);
        return new ResponseEntity<>(authorizerList, HttpStatus.OK);

    }

    @DeleteMapping(value = "/v1/authorizer/delete", name = "Borra un autorizador")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id")) id = UUID.fromString(value);
        });
        repositoryAuthorizer.delete(id);
        return new ResponseEntity<>("Autorizador borrado",HttpStatus.OK);
    }
}
