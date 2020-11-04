package com.mindware.workflow.spring.rest.workUpReview;

import com.mindware.workflow.core.entity.workupReview.WorkUpReview;
import com.mindware.workflow.core.service.data.workUpReview.RepositoryWorkUpReview;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.workUpReview.CreateWorkUpReview;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class WorkUpReviewController {
    private static Logger LOGGER = LoggerFactory.getLogger(WorkUpReviewController.class);
    private UUID idWorkUpReview;
    private Integer numberRequest;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryWorkUpReview repository;

    @PostMapping(value ="/v1/workUpReview/add", name = "Crear Observaciones")
    ResponseEntity<WorkUpReview> create(@RequestBody WorkUpReview workUpReview, HttpServletRequest request){
        UseCase<WorkUpReview> useCase = useCaseFactory.create(CreateWorkUpReview.class.getSimpleName(), workUpReview);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(workUpReview, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/workUpReview/getAllWorkUpReviews", name = "Obtener todas los seguimientos")
    ResponseEntity<List<WorkUpReview>> getAllWorkUpReviews(){
        List<WorkUpReview> workUpReviewList = repository.getAllWorkUpReviews();
        return new ResponseEntity<>(workUpReviewList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/wokUpReview/getWorkUpReviewByNumberRequest", name = "Obtener seguiemientos por numero solicitud")
    ResponseEntity<List<WorkUpReview>> getWorkUpReviewByNumberRequest(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });
        List<WorkUpReview> workUpReviewList = repository.getWorkUpReviewByNumberRequest(numberRequest);

        return new ResponseEntity<>(workUpReviewList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/wokUpReview/getWorkUpReviewById", name = "Obtener seguiemientos por ID")
    ResponseEntity<WorkUpReview> getWorkUpReviewById(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id-workup-review")) idWorkUpReview = UUID.fromString(value);
        });
        WorkUpReview workUpReview = repository.getWorkUpReviewById(idWorkUpReview).get();

        return new ResponseEntity<> (workUpReview,HttpStatus.OK);
    }

}
