package com.mindware.workflow.spring.rest.workflowStep;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.config.RequestStage;
import com.mindware.workflow.core.entity.config.States;
import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import com.mindware.workflow.drools.WorkflowStepsRules;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class WorkflowStepsController {

    @Autowired
    RepositoryStageHistory repositoryStageHistory;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryWorkflowProduct repositoryWorkflowProduct;

    private Integer numberRequest;
    private String currentStage;
    private String codeTypeCredit;
    private Integer codeObjectCredit;

    @SneakyThrows
    @GetMapping(value = "/v1/workflowStep/verifyAllStageFinished", name = "Verifica que todas las etapas actuales esten concluidas")
    ResponseEntity<Boolean> verifyAllStageFinished(@RequestHeader Map<String,String> headers){
        ObjectMapper mapper = new ObjectMapper();

        headers.forEach((key,value)->{
            if(key.equals("number-request")) numberRequest = Integer.valueOf(value);
            if(key.equals("current-stage")) currentStage = value;
            if(key.equals("code-type-credidt")) codeTypeCredit = value;
            if(key.equals("code-object-credit")) codeObjectCredit = Integer.valueOf(value);
        });

        List<StageHistory> stageHistoryList = repositoryStageHistory.getByNumberRequest(numberRequest);

        WorkflowProduct workflowProduct = repositoryWorkflowProduct.getByTypeCreditAndObject(codeTypeCredit,codeObjectCredit).get();
        List<RequestStage> requestStageList = mapper.readValue(workflowProduct.getRequestStage(),new TypeReference<List<RequestStage>>(){});

        RequestStage requestStage = requestStageList.stream().filter(r -> r.getStage().equals(currentStage))
                .findFirst().get();

        List<RequestStage> requestStagesSamePosition = requestStageList.stream()
                .filter(r -> r.getPosition().equals(requestStage.getPosition()))
                .collect(Collectors.toList());
        List<States> statesList = new ArrayList<>();
        for(StageHistory sh:stageHistoryList){
            RequestStage r = requestStagesSamePosition.stream().filter(rs -> rs.getStage().equals(sh.getStage()))
                    .findFirst().get();
            List<States> stList = mapper.readValue(r.getStates(), new TypeReference<List<States>>() {});
            States states = stList.stream().filter(st -> st.getState().equals(sh.getState())).findFirst().get();
            statesList.add(states);
        }

        Boolean isAllStageFinished = WorkflowStepsRules.verifyAllStageFinished(stageHistoryList,requestStagesSamePosition,statesList);

        return new ResponseEntity<>(isAllStageFinished, HttpStatus.OK);
    }
}
