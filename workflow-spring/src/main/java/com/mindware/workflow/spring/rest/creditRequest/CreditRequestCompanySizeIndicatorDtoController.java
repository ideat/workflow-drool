package com.mindware.workflow.spring.rest.creditRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.creditRequest.CompanySizeIndicator;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CreditRequestCompanySizeIndicatorDtoController {

    private String city;
    private String loginUser;
    private Integer numberRequest;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequestCompanySizeIndicatorDto repository;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @GetMapping(value = "/v1/creditRequestCompanySizeIndicator/getAll", name = "Obtiene todas las solicitudes e indicadores de tamano de la empresa")
    ResponseEntity<Collection<CreditRequestCompanySizeIndicatorDto>> getAll(){
        List<CreditRequestCompanySizeIndicatorDto> creditRequestCompanySizeIndicatorDtoList = repository.getAll();
        return new ResponseEntity<>(creditRequestCompanySizeIndicatorDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditRequestCompanySizeIndicator/getByCity", name = "Obtiene por ciudad las solicitudes e indicadores de tamano de la empresa")
    ResponseEntity<Collection<CreditRequestCompanySizeIndicatorDto>> getByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("city")) city = value;

        });

        List<CreditRequestCompanySizeIndicatorDto> creditRequestCompanySizeIndicatorDtoList = repository.getByCity(city);
        return new ResponseEntity<>(creditRequestCompanySizeIndicatorDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditRequestCompanySizeIndicator/getByUser", name = "Obtiene por Usuario las solicitudes e indicadores de tamano de la empresa")
    ResponseEntity<Collection<CreditRequestCompanySizeIndicatorDto>> getByUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("login-user")) loginUser = value;

        });

        List<CreditRequestCompanySizeIndicatorDto> creditRequestCompanySizeIndicatorDtoList = repository.getByUser(loginUser);
        return new ResponseEntity<>(creditRequestCompanySizeIndicatorDtoList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/creditRequestCompanySizeIndicator/report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] reportCompanySizeIndicator(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if (key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        ObjectMapper mapper = new ObjectMapper();
        CreditRequestCompanySizeIndicatorDto creditRequestCompanySizeIndicatorDto = repository.getByNumberRequest(numberRequest).get();
        String jsonCompanySizeIndicator = creditRequestCompanySizeIndicatorDto.getCompanySizeIndicator();
        CompanySizeIndicator companySizeIndicator = mapper.readValue(jsonCompanySizeIndicator,CompanySizeIndicator.class);
        companySizeIndicator.setNumberRequest(numberRequest);
        List<CompanySizeIndicator> companySizeIndicatorList = new ArrayList<>();
        companySizeIndicatorList.add(companySizeIndicator);

        InputStream stream = getClass().getResourceAsStream("/template-report/creditRequestCompanySizeIndicator/creditRequestCompanySizeIndicator.jrxml");

        String pathLogo = getClass().getResource("/template-report/img/logo.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,companySizeIndicatorList,params);
        InputStream is = new ByteArrayInputStream(b);
        return IOUtils.toByteArray(is);
    }
}
