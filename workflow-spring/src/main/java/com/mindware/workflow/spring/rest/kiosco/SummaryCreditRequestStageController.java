package com.mindware.workflow.spring.rest.kiosco;

import com.mindware.workflow.core.entity.kiosco.SummaryCreditRequestStage;
import com.mindware.workflow.core.service.data.kiosco.RepositorySummaryCreditRequestStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class SummaryCreditRequestStageController {

    @Autowired
    RepositorySummaryCreditRequestStage repository;

    @GetMapping(value = "/kiosco/getSummaryByIdCard/{idcard}", name = "Obtiene solicitudes pendiente")
    ResponseEntity<Collection<SummaryCreditRequestStage>> getSummaryCreditRequestStageByIdCard(@PathVariable("idcard") String idCard){
        List<SummaryCreditRequestStage> result = repository.findActiveRequestByIdCard(idCard);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
