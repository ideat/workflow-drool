package com.mindware.workflow.spring.rest.stageHistory;

import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.stageHistory.CreateStageHistory;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StageHistoryController {
    private static Logger LOGGER = LoggerFactory.getLogger(StageHistoryController.class);

    private Integer numberRequest;
    private String stage;
    private String state;


    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryStageHistory repositoryStageHistory;

    @PostMapping(value = "/v1/stageHistory/add",name = "Crea Etapa de la solicitud")
    ResponseEntity<StageHistory> create(@RequestBody StageHistory stageHistory, HttpServletRequest request){
        UseCase<StageHistory> useCase = useCaseFactory.create(CreateStageHistory.class.getSimpleName(),stageHistory);
        useCase.execute();

        if(useCase.getResult().isPresent()){

            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(stageHistory,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/stageHistory/getAll", name = "Obtener lista historica de etapas")
    ResponseEntity<Collection<StageHistory>> getAll(){
        List<StageHistory> stageHistories = repositoryStageHistory.getAll();
        return new ResponseEntity<>(stageHistories,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistory/getByNumberRequestStageState",name = "Obtiene lista historico")
    ResponseEntity<Collection<StageHistory>> getByNumberRequestStageState(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) ->{
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("stage")) stage =  value;
            if(key.equals("state")) state = value;
        });
        List<StageHistory> stageHistoryList = repositoryStageHistory.getByNumberRequestStageState(stage,numberRequest,state);
        return new ResponseEntity<>(stageHistoryList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistory/getByNumberRequest/{number_request}",name = "Obtiene lista historico por nro solicitud")
    ResponseEntity<Collection<StageHistory>> getByNumberRequest(@PathVariable("number_request") Integer numberRequest){
        List<StageHistory> stageHistoryList  = repositoryStageHistory.getByNumberRequest(numberRequest);
        return new ResponseEntity<>(stageHistoryList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/stageHistory/getById/{id}", name = "Obtiene Etapa del historico por ID")
    ResponseEntity<StageHistory> getById(@PathVariable("id")UUID id){
        StageHistory stageHistory = repositoryStageHistory.getById(id).get();
        return new ResponseEntity<>(stageHistory,HttpStatus.OK);
    }

    @PutMapping(value = "/v1/stageHistory/update",name = "Actualiza Historico Etapa")
    ResponseEntity<StageHistory> update(@RequestBody StageHistory stageHistory){
        repositoryStageHistory.update(stageHistory);
        return new ResponseEntity<>(stageHistory,HttpStatus.OK);
    }


}
