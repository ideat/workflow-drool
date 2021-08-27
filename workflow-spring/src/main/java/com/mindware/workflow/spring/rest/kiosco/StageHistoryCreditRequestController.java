package com.mindware.workflow.spring.rest.kiosco;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.task.CreateStageHistoryCreditRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StageHistoryCreditRequestController {
    private Integer numberRequest;

    @Autowired
    RepositoryStageHistoryCreditRequestDto repository;

    @Autowired
    RepositoryWorkflowProduct repositoryWorkflowProduct;

    @GetMapping(value = "/kiosco/getDetailByNumberRequest", name = "Obtiene Historial de una solicitud")
    ResponseEntity<Collection<StageHistoryCreditRequestDto>> getDetailByNumberRequest(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = repository.getDetailByNumberRequest(numberRequest);
        List<WorkflowProduct> workflowProductList = repositoryWorkflowProduct.getAll();
        stageHistoryCreditRequestDtoList = CreateStageHistoryCreditRequestDto.generateDetailStageHistoryCreditRequest(stageHistoryCreditRequestDtoList,workflowProductList);

        return new ResponseEntity<>(stageHistoryCreditRequestDtoList, HttpStatus.OK);
    }
}
