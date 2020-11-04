package com.mindware.workflow.spring.rest.config;

import com.mindware.workflow.core.entity.config.TemplateForm;
import com.mindware.workflow.core.service.data.config.RepositoryTemplateForms;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.config.CreateTemplateForms;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
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
public class TemplateFormsController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryTemplateForms repositoryTemplateForms;

    @PostMapping(value = "/v1/templateforms/add", name = "Configurar formularios")
    ResponseEntity<TemplateForm> create (@RequestBody TemplateForm templateForm, HttpServletRequest request){
        UseCase<TemplateForm> useCase = useCaseFactory.create(CreateTemplateForms.class.getSimpleName(), templateForm);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(templateForm,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/templateforms/getAll", name = "Obtener todas las plantillas")
    ResponseEntity<Collection<TemplateForm>> getAll(){
        List<TemplateForm> templateFormList = repositoryTemplateForms.getAll();
        return new ResponseEntity<>(templateFormList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/templateforms/getByNameCategory/{name}/{category}", name = "Obtener plantilla por nombre y categoria")
    ResponseEntity<Optional<TemplateForm>> getByNameCategory(@PathVariable("name") String name, @PathVariable("category") String category){
        Optional<TemplateForm> templateForm = repositoryTemplateForms.getByNameCategory(name,category);

        return new ResponseEntity<>(templateForm,HttpStatus.OK);

    }

    @PutMapping(value = "/v1/templateforms/updateFieldStructure", name = "Actualizar estructura")
    ResponseEntity<TemplateForm> putTemplateForm(@RequestBody TemplateForm templateForm, HttpServletRequest request){
        repositoryTemplateForms.updateFieldStructure(templateForm);
        return new ResponseEntity<>(templateForm,HttpStatus.OK);
    }


}
