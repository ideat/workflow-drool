package com.mindware.workflow.spring.rest.creditRequest;

import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabledApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestEnabledApplicantDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping(value="/rest", produces={"application/json"})
public class CreditRequestEnabledApplicantDtoController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditRequestEnabledApplicantDtoController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryCreditRequestEnabledApplicantDto repository;

    private Instant fromDate;
    private Instant toDate;
    private String enablingUser;
    private String city;


    @GetMapping(value="/v1/creditrequest_enabled_applicant/getAll",name="Obtiene todas las solicitudes desembolsadas")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getAll(){
        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getAll();
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getAllByCity/{city}",name="Obtiene todas las solicitudes de una ciudad")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getAllByCity(@PathVariable("city") String city){
        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getAllByCity(city);
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getByEnablingUser/{enablingUser}",name="Obtiene todas las solicitudes de una ciudad")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getByEnablingUser(@PathVariable("enablingUser") String enablingUser){
        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getByEnablingUser(enablingUser);
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getByLoginUser/{loginUser}",name="Obtiene todas las solicitudes del oficial")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getByLoginUser(@PathVariable("loginUser") String loginUser){
        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getByLoginUser(loginUser);
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }


    @GetMapping(value="/v1/creditrequest_enabled_applicant/getAllEnabled",name="Obtiene todas las solicitudes desembolsadas")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getAllEnabled(){

        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getAllEnabled();
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getAllEnabledByCity",name="Obtiene todas las solicitudes de una ciudad")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getAllEnabledByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("city")) city = value;
        });

        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getAllEnabledByCity(city);
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getAllEnabledByEnablignUser",name="Obtiene todas las solicitudes de una ciudad")
    ResponseEntity<Collection<CreditRequestEnabledApplicantDto>> getAllEnabledByEnablignUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            if(key.equals("enabling-user")) enablingUser = value;
        });
        List<CreditRequestEnabledApplicantDto> creditRequestEnabledApplicantDtoList = repository.getAllEnabledByEnablignUser(enablingUser);
        return new ResponseEntity<>(creditRequestEnabledApplicantDtoList, HttpStatus.OK);
    }

    @GetMapping(value="/v1/creditrequest_enabled_applicant/getEnabledReport",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getEnabledReport(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->{
            if(key.equals("city")) city = value;
            if(key.equals("from-date")) fromDate = LocalDate.parse(value).atStartOfDay(ZoneId.systemDefault()).toInstant();
            if(key.equals("to-date")) toDate = LocalDate.parse(value).atStartOfDay(ZoneId.systemDefault()).toInstant();
        });

        List<CreditRequestEnabledApplicantDto> result = complementInfo(repository.getEnabledReport(city,fromDate,toDate));

        InputStream stream = getClass().getResourceAsStream("/template-report/creditRequestEnabled/creditRequestEnabled.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault());
        String fromDateFormat = formatter.format(fromDate);
        String toDateFormat = formatter.format(toDate);
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("fromDate",fromDateFormat);
        params.put("toDate",toDateFormat);


        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,result,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    private List<CreditRequestEnabledApplicantDto> complementInfo( List<CreditRequestEnabledApplicantDto> list){
        List<CreditRequestEnabledApplicantDto> result = new ArrayList<>();
        Instant current = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault());
        for(CreditRequestEnabledApplicantDto c:list){
            Integer timeEnabledSeconds = c.getHoursEnabled()*3600;
            if(c.getFinishedDateTime().isBefore(Instant.now())){
                c.setState("CERRADO");
                c.setTimeLeft("");
            }else{
                c.setState("ABIERTO");
                Long seconds = current.getEpochSecond() - c.getEnabledDateTime().getEpochSecond();
                String left = String.format("Dias: %s \n Horas: %s, Minutos: %s ",(seconds / 86400),(seconds / 3600 % 24),(seconds / 60 % 60));
                c.setTimeLeft(left);

            }
            c.setEnablingDateTimeStr(formatter.format(c.getEnabledDateTime()));
            String enabled = String.format("Dias: %s \n Horas: %s Minutos: %s ",
                    (timeEnabledSeconds/86400),(timeEnabledSeconds / 3600 % 24), (timeEnabledSeconds / 60 % 60));
            c.setTimeEnabled(enabled);
            c.setFullName(c.getFirstName() +" " + c.getLastName());
            result.add(c);
        }

        return result;
    }
}
