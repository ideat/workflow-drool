package com.mindware.workflow.spring.rest.historyChangeResponsibleDto;

import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleDto;
import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class HistoryChangeResponsibleDtoController {
    private static Logger LOGGER = LoggerFactory.getLogger(HistoryChangeResponsibleDtoController.class);


    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryHistoryChangeResponsibleDto repositoryHistoryChangeResponsibleDto;

    @GetMapping(value = "/v1/historyChangeResponsibleDto/getDataByRolOficial/{loginUser}",name = "Operaciones asignadas a un oficial")
    ResponseEntity<List<HistoryChangeResponsibleDto>> getDataByRolOficial(@PathVariable("loginUser") String loginuser){
        List<HistoryChangeResponsibleDto> result = repositoryHistoryChangeResponsibleDto.getDataByRolOficial(loginuser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/historyChangeResponsibleDto/getDataByRolLegal/{loginUser}",name = "Operaciones asignadas a un oficial")
    ResponseEntity<List<HistoryChangeResponsibleDto>> getDataByRolLegal(@PathVariable("loginUser") String loginuser){
        List<HistoryChangeResponsibleDto> result = repositoryHistoryChangeResponsibleDto.getDataByRolLegal(loginuser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/historyChangeResponsibleDto/getDataByRolAuthorizer/{loginUser}",name = "Operaciones asignadas a un oficial")
    ResponseEntity<List<HistoryChangeResponsibleDto>> getDataByRolAuthorizer(@PathVariable("loginUser") String loginuser){
        List<HistoryChangeResponsibleDto> result = repositoryHistoryChangeResponsibleDto.getDataByRolAuthorizer(loginuser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/v1/historyChangeResponsibleDto/getDataUserWorkflow/{loginUser}",name = "Operaciones asignadas a un oficial")
    ResponseEntity<List<HistoryChangeResponsibleDto>> getDataUserWorkflow(@PathVariable("loginUser") String loginuser){
        List<HistoryChangeResponsibleDto> result = repositoryHistoryChangeResponsibleDto.getDataUserWorkflow(loginuser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
