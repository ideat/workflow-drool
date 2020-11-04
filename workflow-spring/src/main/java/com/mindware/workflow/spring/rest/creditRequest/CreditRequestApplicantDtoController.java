package com.mindware.workflow.spring.rest.creditRequest;

import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditRequestApplicantDtoController {

    private static Logger LOGGER = LoggerFactory.getLogger(CreditRequestApplicantDtoController.class);
    private Integer numberRequest;
    private String loginUser;
    private String typeRelation;
    private String cityOffice;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto;

    @GetMapping(value = "/v1/creditrequestapplicant/getAll", name = "Listado de solicitudes de credito")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getAllCreditRequestApplicant(){
        List<CreditRequestApplicantdto> creditRequestApplicantsDto = repositoryCreditRequestApplicantDto.getAll();
        return new ResponseEntity<>(creditRequestApplicantsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getByUser")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getByIdUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("login-user")) loginUser = value;
        });

        List<CreditRequestApplicantdto> creditRequestApplicants = repositoryCreditRequestApplicantDto.getByLoginUser(loginUser);
        return new ResponseEntity<>(creditRequestApplicants,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getByLoginNumberRequest")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getByLoginNumberRequest(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) -> {
            if (key.equals("loginuser")) loginUser = value;
            if (key.equals("numberrequest")) numberRequest = Integer.parseInt(value);
        });
        List<CreditRequestApplicantdto> creditRequestApplicants = repositoryCreditRequestApplicantDto.getByLoginUserNumberRequest(loginUser,numberRequest);
        return new ResponseEntity<>(creditRequestApplicants,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getByNumberRequest")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getByNumberRequest(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) -> {
            if (key.equals("numberrequest")) numberRequest = Integer.parseInt(value);
        });
        List<CreditRequestApplicantdto> creditRequestApplicants = repositoryCreditRequestApplicantDto.getByNumberRequest(numberRequest);
        return new ResponseEntity<>(creditRequestApplicants,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getByLoginTypeRelation")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getByLoginUserTypeRelation(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) -> {
            if (key.equals("loginuser")) loginUser = value;
            if (key.equals("typerelation")) typeRelation = value;
        });
        List<CreditRequestApplicantdto> creditRequestApplicants = repositoryCreditRequestApplicantDto.getByLoginUserTypeRelation(loginUser,typeRelation);
        return new ResponseEntity<>(creditRequestApplicants,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditrequestapplicant/getAllByCity")
    ResponseEntity<Collection<CreditRequestApplicantdto>> getAllByCity(@RequestHeader Map<String,String> headers){

        headers.forEach((key,value) -> {
            if (key.equals("city-office")) cityOffice = value;

        });
        List<CreditRequestApplicantdto> creditRequestApplicants = repositoryCreditRequestApplicantDto.getAllByCity(cityOffice);
        return new ResponseEntity<>(creditRequestApplicants,HttpStatus.OK);
    }

}
