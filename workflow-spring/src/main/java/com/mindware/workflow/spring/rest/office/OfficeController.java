package com.mindware.workflow.spring.rest.office;

import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.office.CreateOffice;
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
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = { "application/json" })
public class OfficeController {
    private static Logger LOGGER = LoggerFactory.getLogger(OfficeController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryOffice repositoryOffice;

    private Integer internalCode;

    @PostMapping(value = "/v1/office/add", name = "Crear sucursal")
    ResponseEntity<Office> create(@RequestBody Office office, HttpServletRequest request){

        UseCase<Office> useCase = useCaseFactory.create(CreateOffice.class.getSimpleName(),office);
        useCase.execute();
        if (useCase.getResult().isPresent()) {
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(office, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/office/getAll", name = "Obtener listado oficinas")
    ResponseEntity<Collection<Office>> getOffices(){
        List<Office> offices = repositoryOffice.getAllOffices();
        return new ResponseEntity<>(offices,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/office/getOfficeCity", name = "Obtener listado ciudades con oficinas")
    ResponseEntity<Collection<Office>> getOfficeCity(){
        List<Office> offices = repositoryOffice.getOfficeCity();
        return new ResponseEntity<>(offices,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/office/getByCode", name = "Obtiene oficina por su codigo")
    ResponseEntity<Office> getOfficeByInternalCode(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("internal-code")) internalCode = Integer.parseInt(value);
        });
        Office office = repositoryOffice.getOfficeByInternalCode(internalCode).get();
        return new ResponseEntity<>(office,HttpStatus.OK);
    }

    @PutMapping(value = "/v1/office/updateSignatorie", name = "Actualizar responsables oficina")
    ResponseEntity<Office> putOfficeSignatorie(@RequestBody Office office, HttpServletRequest request){
        repositoryOffice.updateOfficeSignatorie(office);
        return new ResponseEntity<>(office,HttpStatus.OK);
    }


}
