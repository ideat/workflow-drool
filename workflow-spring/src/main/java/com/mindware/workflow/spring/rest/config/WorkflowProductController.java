package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateWorkflowProduct;
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
public class WorkflowProductController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryWorkflowProduct repository;

    @PostMapping(value = "/v1/workflowproduct/add", name = "Crear Workflow Producto")
    ResponseEntity<WorkflowProduct> create (@RequestBody WorkflowProduct workflowProduct, HttpServletRequest request){
        UseCase<WorkflowProduct> useCase = useCaseFactory.create(CreateWorkflowProduct.class.getSimpleName(),workflowProduct);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(workflowProduct,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/workflowproduct/getAll", name = "Obtener todos los flujos")
    ResponseEntity<Collection<WorkflowProduct>> getAll(){
        List<WorkflowProduct> workflowProducts = repository.getAll();
        return new ResponseEntity<>(workflowProducts,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/workflowproduct/getByCode/{code}", name = "Obtener flujo de un producto")
    ResponseEntity<WorkflowProduct> getByCode(@PathVariable("code") String code){
        WorkflowProduct workflowProduct = new WorkflowProduct();
        if (repository.getByCode(code).isPresent()) {
            workflowProduct = repository.getByCode(code).get();
        }
        return new ResponseEntity<>(workflowProduct,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/workflowproduct/getByTypeCreditAndObject/{codetypecredit}/{codeobjectcredit}", name = "Obtener flujo por tipo y objeto de credito")
    ResponseEntity<WorkflowProduct> getByTypeCreditAndObject(@PathVariable("codetypecredit") String codeTypeCredit,
                                                             @PathVariable("codeobjectcredit") String codeObjectCredit){
        WorkflowProduct workflowProduct = new WorkflowProduct();
        if (repository.getByTypeCreditAndObject(codeTypeCredit,Integer.valueOf(codeObjectCredit)).isPresent()) {
            workflowProduct = repository.getByTypeCreditAndObject(codeTypeCredit,Integer.valueOf(codeObjectCredit)).get();
        }
        return new ResponseEntity<>(workflowProduct,HttpStatus.OK);
    }

}
