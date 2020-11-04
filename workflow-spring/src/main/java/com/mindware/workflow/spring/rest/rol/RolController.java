package com.mindware.workflow.spring.rest.rol;

import com.mindware.workflow.core.entity.rol.Rol;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.rol.CreateRol;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class RolController {
    private static Logger LOGGER = LoggerFactory.getLogger(RolController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryRol repository;

    @PostMapping(value = "/rol/v1/add", name = "Crear Rol")
    ResponseEntity<Rol> create(@RequestBody Rol rol, HttpServletRequest request){
        UseCase<Rol> useCase = useCaseFactory.create(CreateRol.class.getSimpleName(),rol);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(rol,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/rol/v1/getAll", name = "Obtener los roles")
    ResponseEntity<Collection<Rol>> getAll(){
        List<Rol> rolList = repository.getAll();
        return new ResponseEntity<>(rolList,HttpStatus.OK);
    }

    @GetMapping(value = "/rol/v1/getById/{id}", name = "Obtener Rol por ID")
    ResponseEntity<Rol> getById(@PathVariable("id")UUID id){
        Rol rol = repository.getById(id).get();
        return new ResponseEntity<>(rol,HttpStatus.OK);
    }

    @GetMapping(value = "/rol/v1/getByName/{name}", name = "Obtener Rol por nombre")
    ResponseEntity<Rol> getRolByName(@PathVariable("name")String name){
        Rol rol = repository.getRolByName(name).get();
        return new ResponseEntity<>(rol,HttpStatus.OK);
    }

    @PutMapping(value = "/rol/v1/update", name = "Actualizar Rol")
    ResponseEntity<Rol> update(@RequestBody Rol rol, HttpServletRequest request){
        repository.update(rol);
        return new ResponseEntity<>(rol,HttpStatus.OK);
    }

}
