package com.mindware.workflow.spring.rest.comercial.cliente;

import com.mindware.workflow.core.entity.comercial.client.Client;
import com.mindware.workflow.core.service.data.comercial.client.RepositoryClient;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.comercial.client.CreateClient;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
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
public class ClientController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryClient repository;

    private String loginUser;
    private UUID id;

    @PostMapping(value = "/v1/comercial/client/add", name = "Crear Cliente")
    ResponseEntity<Client> create(@RequestBody Client client, HttpServletRequest request){
        UseCase<Client> useCase = useCaseFactory.create(CreateClient.class.getSimpleName(),client);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(client,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/comercial/client/getAll", name = "Obtiene todos los clientes")
    ResponseEntity<Collection<Client>> getAll(){
        List<Client> clientList = repository.getAll();
        return new ResponseEntity<>(clientList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/comercial/client/getByUser", name = "Obtiene cliente de un usuario")
    ResponseEntity<Collection<Client>> getByUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("login-user")) loginUser = value;
        });
        List<Client> clientList = repository.getByUser(loginUser);
        return new ResponseEntity<>(clientList,HttpStatus.OK);
    }
}
