package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.Product;
import com.mindware.workflow.core.service.data.config.RepositoryProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateProduct;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ProductController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryProduct repositoryProduct;

    @PostMapping(value = "/product/v1/add", name = "Crear Producto Crediticio")
    ResponseEntity<Product> create(@RequestBody Product product, HttpServletRequest request){
        UseCase<Product> useCase = useCaseFactory.create(CreateProduct.class.getSimpleName(),product);
        useCase.execute();
        if (useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
    }
}
