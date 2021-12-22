package com.mindware.workflow.spring.rest.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringProduct;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditScoring.CreateScoringProduct;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ScoringProductController {
    private static Logger LOGGER = LoggerFactory.getLogger(ScoringProductController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryScoringProduct repositoryScoringProduct;

    @PostMapping(value = "/v1/scoringproduct/add", name = "Crear Scoring para un producto crediticio")
    ResponseEntity<ScoringProduct> create(@RequestBody ScoringProduct scoringProduct,  HttpServletRequest request){
        UseCase<ScoringProduct> useCase = useCaseFactory.create(CreateScoringProduct.class.getSimpleName(),scoringProduct);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(scoringProduct, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/scoringproduct/getByName/{name}", name = "Obtiene Scoring por nombre")
    ResponseEntity<ScoringProduct> getByName(@PathVariable("name") String name){
        Optional<ScoringProduct> scoringProduct = repositoryScoringProduct.getByName(name);
        if(scoringProduct.isPresent()){
            return new ResponseEntity(scoringProduct, HttpStatus.OK);
        }else{
            throw new UseCaseException("Nombre Scoring no esta registrado");
        }
    }

    @GetMapping(value = "/v1/scoringproduct/getById/{id}", name = "Obtiene Scoring por nombre")
    ResponseEntity<ScoringProduct> getById(@PathVariable("id") String id){
        Optional<ScoringProduct> scoringProduct = repositoryScoringProduct.getById(UUID.fromString(id));
        if(scoringProduct.isPresent()){
            return new ResponseEntity(scoringProduct, HttpStatus.OK);
        }else{
            throw new UseCaseException("Nombre Scoring no esta registrado");
        }
    }

    @GetMapping(value = "/v1/scoringproduct/getAll", name = "Obtiene todas las configuraciones de scoring")
    ResponseEntity<Collection<ScoringProduct>> getAll(){
        List<ScoringProduct> scoringProductList = repositoryScoringProduct.getAll();
        return new ResponseEntity<>(scoringProductList,HttpStatus.OK);
    }
}
