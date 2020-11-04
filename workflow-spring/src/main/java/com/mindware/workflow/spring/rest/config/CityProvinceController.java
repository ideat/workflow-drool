package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.CityProvince;
import com.mindware.workflow.core.service.data.cityProvince.RepositoryCityProvince;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.cityProvince.CreateCityProvince;
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
public class CityProvinceController {

    private UUID id;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCityProvince repository;

    private String city;

    @PostMapping(value = "/v1/cityProvince/add", name = "Crear ciudad y provincias")
    ResponseEntity<CityProvince> create(@RequestBody CityProvince cityProvince, HttpServletRequest request){
        UseCase<CityProvince > useCase = useCaseFactory.create(CreateCityProvince.class.getSimpleName(),cityProvince);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(cityProvince,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/cityProvince/getAll", name = "Obtiene  todas las ciudades y provincias")
    ResponseEntity<Collection<CityProvince>> getAll(){
        List<CityProvince> cityProvinceList = repository.getAll();
        return new ResponseEntity<>(cityProvinceList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/cityProvince/getByCity", name = "Obtiene ciudad por su nombre")
    ResponseEntity<CityProvince> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("city")) city = value;
        });

        CityProvince cityProvince = repository.getByCity(city).get();
        return new ResponseEntity<>(cityProvince,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/cityProvince/delete", name = "Borra una ciudad y sus provincias")
    ResponseEntity<String> delete(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value)->{
            if(key.equals("id")) id = UUID.fromString(value);
        });
        repository.delete(id);
        return new ResponseEntity<>("Ciudad y provincias elminadas",HttpStatus.OK);
    }


}
