package com.mindware.workflow.spring.rest.stageHistory;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.task.CreateStageHistoryCreditRequestDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StageHistoryCreditRequestDtoController {
    private Integer numberRequest;
    private String user;
    private String state;
    private String city;
    private String stage;
    private String rol;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryStageHistoryCreditRequestDto repository;

    @Autowired
    RepositoryWorkflowProduct repositoryWorkflowProduct;

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getByUserNumberRequest", name="Obtiene Historial por Usuario y Numero de solicitud")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getByUserNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("user")) user = value;
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });
        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getByUserNumberRequest(user,numberRequest);

        return new ResponseEntity<>(stageHistoryCreditRequestDtoList, HttpStatus.OK);
    }

   @GetMapping(value = "/v1/stageHistoryCreditRequest/getByUserNumberRequestState", name = "Obtiene Historial por Usuario, Numero solicitud y estado")
   ResponseEntity<Collection<StageHistoryCreditRequestDto>> getByUserNumberRequestState(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("state")) state = value;
        });
       String[] states = state.split(",");
       List<String> stateList = Arrays.asList(states);
        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getByUserNumberRequestState(user,numberRequest,stateList);
        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
   }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getByCity", name = "Obtiene Historial por ciudad")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
        });
        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getByCity(city);
        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getByUserRolState", name = "Obtiene Historial por Usuario, Numero solicitud y estado")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getByUserRolState(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
            if(key.equals("rol")) rol = value;
            if(key.equals("state")) state = value;
        });
        String[] states = state.split(",");
        List<String> stateList = Arrays.asList(states);
        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getByUserRolState(user,rol,stateList);
        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getDetailByUserRol", name = "Obtiene Detalle del Historial por Usuario y Rol")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getDetailByUserRol(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
            if(key.equals("rol")) rol = value;
            if(key.equals("state")) state = value;
        });
        String[] states = state.split(",");
        List<String> stateList = Arrays.asList(states);

        List<StageHistoryCreditRequestDto> stageHCR = repository.getDetailByUserRol(user,rol);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto
                .generateDetailStageHistoryCreditRequest(stageHCR,workflowProductList);


        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getDetailByUserRolCity", name = "Obtiene Detalle del Historial por Usuario y Rol")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getDetailByUserRolCity(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
            if(key.equals("rol")) rol = value;
            if(key.equals("state")) state = value;
            if(key.equals("city")) city = value;
        });
        String[] states = state.split(",");
        List<String> stateList = Arrays.asList(states);

        List<StageHistoryCreditRequestDto> stageHCR = repository.getDetailByUserRolCity(user,rol,city);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto
                .generateDetailStageHistoryCreditRequest(stageHCR,workflowProductList);


        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getResumByUserRol", name = "Obtiene Detalle del Historial por Usuario y Rol")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getResumByUserRolState(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
            if(key.equals("rol")) rol = value;
            if(key.equals("state")) state = value;
            if(key.equals("city")) city = value;
        });
        String[] states = state.split(",");
        List<String> stateList = Arrays.asList(states);
        List<StageHistoryCreditRequestDto> stageHCR = new ArrayList<>();
        if(city.equals("")) {
            stageHCR = repository.getDetailByUserRol(user, rol);
        }else{
            stageHCR = repository.getDetailByUserRolCity(user, rol,city);
        }
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();

        List<StageHistoryCreditRequestDto> detailStageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto
                .generateDetailStageHistoryCreditRequest(stageHCR,workflowProductList);

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto
                .generateResumStageHistoryCreditRequestDto(detailStageHistoryCreditRequestDtoList,stateList);

        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getByStateRol", name = "Obtiene Historial por Estado y Etapa")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getByStateRol(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("state")) state = value;
            if(key.equals("rol")) rol = value;
        });
        String[] states = state.split(",");
        List<String> stateList = Arrays.asList(states);

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getByStateRol(stateList,rol);
        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getAll", name = "Obtiene Todo el Historial")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getAll(){

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getAll();
        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getGlobalDetailByCity", name = "Obtiene Historial Global por Ciudad")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getGlobalDetailByCity(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
        });

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getGlobalDetailByCity(city);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();
        stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto.generateDetailStageHistoryCreditRequest(stageHistoryCreditRequestDtoList,workflowProductList);
        List<StageHistoryCreditRequestDto> resultList = CreateStageHistoryCreditRequestDto
                .generateGlobalResumeStageHistoryCreditRequestDto(stageHistoryCreditRequestDtoList);

        return new ResponseEntity<>(resultList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getGlobalDetailByUser", name = "Obtiene Historial Global de un oficial")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getGlobalDetailByUser(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("user")) user = value;
        });

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getGlobalDetailByUser(user);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();
        stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto.generateDetailStageHistoryCreditRequest(stageHistoryCreditRequestDtoList,workflowProductList);
        List<StageHistoryCreditRequestDto> resultList = CreateStageHistoryCreditRequestDto
                .generateGlobalResumeStageHistoryCreditRequestDto(stageHistoryCreditRequestDtoList);

        return new ResponseEntity<>(resultList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getGlobalDetail", name = "Obtiene Todo el Historial")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getGlobalDetail() throws IOException {

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getGlobalDetail();
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();
        stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto.generateDetailStageHistoryCreditRequest(stageHistoryCreditRequestDtoList,workflowProductList);
        List<StageHistoryCreditRequestDto> resultList = CreateStageHistoryCreditRequestDto
                .generateGlobalResumeStageHistoryCreditRequestDto(stageHistoryCreditRequestDtoList);
        return new ResponseEntity<>(resultList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistoryCreditRequest/getDetailByNumberRequest", name = "Obtiene Historial de una solicitud")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getDetailByNumberRequest(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getDetailByNumberRequest(numberRequest);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();
        stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto.generateDetailStageHistoryCreditRequest(stageHistoryCreditRequestDtoList,workflowProductList);

        return new ResponseEntity<>(stageHistoryCreditRequestDtoList,HttpStatus.OK);
    }
}
