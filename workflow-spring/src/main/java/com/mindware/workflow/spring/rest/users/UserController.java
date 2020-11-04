package com.mindware.workflow.spring.rest.users;


import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.service.data.users.RepositoryUsersOfficeDto;
import com.mindware.workflow.core.service.data.users.dto.UsersOfficeDto;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.users.CreateUser;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryUsersOfficeDto repositoryUsersOfficeDto;

//    @Autowired
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private String pass;
    private Integer codeOffice;
    private String city;

    @PostMapping(value = "/user/v1/add", name = "Crear usuarios")
    ResponseEntity<Users> create(@RequestBody Users users, HttpServletRequest request){
        users.setPassword(encoder.encode(users.getPassword()));
        UseCase<Users> useCase = useCaseFactory.create(CreateUser.class.getSimpleName(),users);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user/v1/getAll", name = "Obtener todos los usuarios")
    ResponseEntity<Collection<Users>> getAllUsers(){
        List<Users> users = repositoryUsers.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/user/v1/getById/{id}", name = "Obtiene Usuario por ID")
    ResponseEntity<Users> getById(@PathVariable("id") UUID id){
        Users users = repositoryUsers.getUserById(id).get();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/user/v1/getByLogin/{login}", name = "Obtiene usuario por Login")
    ResponseEntity<Users> getByIdUser(@PathVariable("login") String login){
        Users users = repositoryUsers.getUserByIdUser(login).get();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/user/v1/getByRol/{rol}", name = "Obtener todos los usuarios del rol")
    ResponseEntity<Collection<Users>> getByRol(@PathVariable("rol") String rol){
        List<Users> users = repositoryUsers.getByRol(rol);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PutMapping(value = "/user/v1/updatePassword", name = "Actualiza password del usuario")
    ResponseEntity<Users> updatePassword(@RequestBody Users users){
        users.setPassword(encoder.encode(users.getPassword()));
        users.setDateUpdatePassword(LocalDate.now());
//        users.setState("ACTIVO");
        repositoryUsers.updatePassword(users);
        return  new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/user/v1/getPassword", name = "Retorna password encriptado")
    ResponseEntity<String> getPassword(@RequestHeader("pass") Map<String,String> headers){
        headers.forEach((key,value) -> {
            if (key.equals("pass")) pass = value;
        });
        String encripted = encoder.encode(pass);
        return new ResponseEntity<>(encripted,HttpStatus.OK);
    }

    @GetMapping(value = "user/v1/getByCodeOffice", name = "Usuarios por ")
    ResponseEntity<Collection<Users>> getByCodeOffice(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("code-office")) codeOffice = Integer.parseInt(value);
        });

        List<Users> usersList = repositoryUsers.getByCodeOffice(codeOffice);
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    @PutMapping(value ="/user/v1/updateUser", name = "Actualiza datos del usuario")
    ResponseEntity<Users> updateUser(@RequestBody Users users, HttpServletRequest request){
        repositoryUsers.updateUser(users);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/userOffice/v1/getByCityAndRol/{city}/{rol}", name = "Usuarios por ciudad y rol")
    ResponseEntity<Collection<UsersOfficeDto>> getUserOfficeByCity(@PathVariable("city")  String city, @PathVariable("rol") String rol){

        List<UsersOfficeDto> usersList = repositoryUsersOfficeDto.getByCityAndRol(city,rol);
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    @GetMapping(value = "/userOffice/v1/getByRol/{rol}", name = "Usuarios por rol")
    ResponseEntity<Collection<UsersOfficeDto>> getUserOfficeByRol(@PathVariable("rol")  String rol){

        List<UsersOfficeDto> usersList = repositoryUsersOfficeDto.getByRol(rol);
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    @GetMapping(value = "/userOffice/v1/getByInternalCodeOffice/{codeoffice}", name = "Usuarios por idOficina")
    ResponseEntity<Collection<UsersOfficeDto>> getByInternalCodeOffice(@PathVariable("codeoffice")  String codeoffice){

        List<UsersOfficeDto> usersList = repositoryUsersOfficeDto.getByInternalCodeOffice(Integer.valueOf(codeoffice));
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }
}
