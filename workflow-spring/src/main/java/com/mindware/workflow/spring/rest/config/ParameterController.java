package com.mindware.workflow.spring.rest.config;


import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateParameter;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ParameterController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryParameter repositoryParameter;

    @PostMapping(value = "/v1/parameter/add", name = "Crear parametro")
    ResponseEntity<Parameter> create (@RequestBody Parameter parameter, HttpServletRequest request){
        UseCase<Parameter> useCase = useCaseFactory.create(CreateParameter.class.getSimpleName(),parameter);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(parameter,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/parameter/getAll", name = "Obtener todos los parametros")
    ResponseEntity<Collection<Parameter>> getAllParameters(){
        List<Parameter> parameters = repositoryParameter.getAllParameters();
        return new ResponseEntity<>(parameters,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/parameter/getParameterByCategory/{category}", name = "Obtener parametros por categoria")
    ResponseEntity<Collection<Parameter>> getParametersbyCategory(@PathVariable("category") String category){
        List<Parameter> parameters = repositoryParameter.getParametersByCategory(category);
        return new ResponseEntity<>(parameters,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/parameter/getParameterByCategories/{category}", name = "Obtener parametros por categoria")
    ResponseEntity<Collection<Parameter>> getParametersbyCategories(@PathVariable("category") String category){
        String[] categories = category.split(",");
        List<String> categoryList = Arrays.asList(categories);
        List<Parameter> parameters = repositoryParameter.getAllByCategories(categoryList);
        return new ResponseEntity<>(parameters,HttpStatus.OK);
    }
}
