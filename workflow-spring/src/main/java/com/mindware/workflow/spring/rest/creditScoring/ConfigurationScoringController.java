package com.mindware.workflow.spring.rest.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryConfigurationScoring;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.creditScoring.CreateConfigurationScoring;
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

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ConfigurationScoringController {

    private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationScoringController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryConfigurationScoring repositoryConfigurationScoring;

    @PostMapping(value = "/v1/scoring/add", name = "Crear configuracion de scoring")
    ResponseEntity<ConfigurationScoring> create(@RequestBody ConfigurationScoring configurationScoring, HttpServletRequest request){
        UseCase<ConfigurationScoring> useCase = useCaseFactory.create(CreateConfigurationScoring.class.getSimpleName(),configurationScoring);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(configurationScoring,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/scoring/configurationScoringList", name = "Obtiene la lista de configuraciones")
    ResponseEntity<Collection<ConfigurationScoring>> getAllConfigurationScoring(){
        List<ConfigurationScoring> configurationScoringList = repositoryConfigurationScoring.configurationScoringList();
        return new ResponseEntity<>(configurationScoringList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/scoring/getByProduct", name = "Obtiene configuracion del producto")
    ResponseEntity<ConfigurationScoring> getByProduct(@PathVariable("product") String product){
        Optional<ConfigurationScoring> configurationScoring = repositoryConfigurationScoring.getByCategory(product);
        if(configurationScoring.isPresent()){
            return new ResponseEntity(configurationScoring,HttpStatus.OK);
        }else{
            throw new UseCaseException("Producto no tiene configuracion registrada");
        }
    }
}
