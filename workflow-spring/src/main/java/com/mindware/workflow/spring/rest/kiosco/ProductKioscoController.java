package com.mindware.workflow.spring.rest.kiosco;

import com.mindware.workflow.core.entity.kiosco.ProductKiosco;
import com.mindware.workflow.core.service.data.kiosco.RepositoryProductKiosco;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.kiosco.CreateProductKiosco;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ProductKioscoController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryProductKiosco repositoryProductKiosco;

    @PostMapping(value = "/kiosco/product/add", name = "Crear Producto en kiosco")
    ResponseEntity<ProductKiosco> create(@RequestBody ProductKiosco productKiosco, HttpServletRequest request){
        UseCase<ProductKiosco> useCase = useCaseFactory.create(CreateProductKiosco.class.getSimpleName(),productKiosco);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(productKiosco,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/kiosco/product/getAll", name = "Obtiene todos los productos")
    ResponseEntity<Collection<ProductKiosco>> getAll(){
        List<ProductKiosco> productKioscoList = repositoryProductKiosco.getAll();
        return new ResponseEntity<>(productKioscoList,HttpStatus.OK);
    }



}
