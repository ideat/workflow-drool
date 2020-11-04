package com.mindware.workflow.spring.rest.contract;

import com.mindware.workflow.core.entity.contract.TemplateContract;
import com.mindware.workflow.core.service.data.contract.RepositoryTemplateContract;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.contract.CreateTemplateContract;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class TemplateContractController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryTemplateContract repository;

    @Value("${path_template}")
    private String pathTemplate;

    @PostMapping(value = "/v1/templateContract/add", name = "Crear una nueva Plantilla")
    ResponseEntity<TemplateContract> create(@RequestBody TemplateContract templateContract, HttpServletRequest request){
        Path path = Paths.get(pathTemplate +templateContract.getFileName());
        templateContract.setPathContract(path.toString());
        UseCase<TemplateContract> useCase = useCaseFactory.create(CreateTemplateContract.class.getSimpleName(),templateContract);
        useCase.execute();

        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(templateContract,HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/templateContract/upload", name = "Sube plantilla contrato")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file")MultipartFile uploadFile, @RequestParam("filename") String filename){

        if (uploadFile.isEmpty()) {
            return new ResponseEntity("Por favor seleccione un archivo", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadFile),filename);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    private void saveUploadedFiles(List<MultipartFile> files, String filename) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathTemplate +filename);
            Files.write(path, bytes);

        }

    }

    @GetMapping(value = "/v1/templateContract/getAll", name = "Obtiene todas las plantillas")
    ResponseEntity<Collection<TemplateContract>> getAll(){
        List<TemplateContract> templateContractList = repository.getAll();
        return new ResponseEntity<>(templateContractList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/templateContract/getById/{id}", name = "Obtiene plantilla por ID")
    ResponseEntity<TemplateContract> getAById(@PathVariable("id")UUID id){
        TemplateContract templateContract = repository.getById(id).get();
        return new ResponseEntity<>(templateContract,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/templateContract/getByFileName/{filename}", name = "Obtiene plantilla por Nombre plantilla")
    ResponseEntity<TemplateContract> getByFileName(@PathVariable("filename")String fileName){
        Optional<TemplateContract> templateContract = repository.getByFileName(fileName);
        if(templateContract.isPresent()) {
            return new ResponseEntity<>(templateContract.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new TemplateContract(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/templateContract/getAllActive/{active}", name = "Obtiene plantilla por su estado")
    ResponseEntity<Collection<TemplateContract>> getAllActive(@PathVariable("active")String active){
        List<TemplateContract> templateContractList = repository.getAllActive(active);
        return new ResponseEntity<>(templateContractList,HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/templateContract/delete/{id}", name = "Borra una plantilla")
    ResponseEntity<String> delete(@PathVariable("id") UUID id){
        repository.delete(id);
        return new ResponseEntity<>("Plantilla Borrada",HttpStatus.OK);
    }
}
