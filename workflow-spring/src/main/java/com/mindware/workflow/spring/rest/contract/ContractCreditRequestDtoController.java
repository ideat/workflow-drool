package com.mindware.workflow.spring.rest.contract;

import com.mindware.workflow.core.service.data.contract.RepositoryContractCreditRequestDto;
import com.mindware.workflow.core.service.data.contract.dto.ContractCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.nio.ch.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ContractCreditRequestDtoController {
    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryContractCreditRequestDto repository;

    private String loginUser;
    private String city;
    private String pathContract;

    @GetMapping(value = "/v1/contractCreditRequestDto/getAll",name = "Obtiene todas las solicitudes en fase de contrato")
    ResponseEntity<Collection<ContractCreditRequestDto>> getAll(){
        List<ContractCreditRequestDto> list = repository.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/contractCreditRequestDto/getByCity", name = "Obtiene solicitudes por ciudad en fase de contrato")
    ResponseEntity<Collection<ContractCreditRequestDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
        });

        List<ContractCreditRequestDto> list = repository.getByCity(city);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/contractCreditRequestDto/getByUser", name = "Obtiene solicitudes de un oficial en fase de contrato")
    ResponseEntity<Collection<ContractCreditRequestDto>> getByUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("login-user")) loginUser = value;
        });

        List<ContractCreditRequestDto> list = repository.getByUser(loginUser);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/contractCreditRequestDto/getFileContract", name = "Obtiene el contrago generado")
    public @ResponseBody byte[] getFileContract(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value)->{
            if(key.equals("path-contract")) pathContract = value;
        });

        Path path = Paths.get(pathContract);

        byte[] bFile = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(bFile);
        return IOUtils.toByteArray(is);

    }
}
