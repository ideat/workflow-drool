package com.mindware.workflow.spring.rest.historyChangeResponsibleReport;

import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleReport;
import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleReport;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class HistoryChangeResponsibleReportController {
    private static Logger LOGGER = LoggerFactory.getLogger(HistoryChangeResponsibleReportController.class);

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryHistoryChangeResponsibleReport repositoryHistoryChangeResponsibleReport;

    private String city;
    private LocalDate startDate;
    private LocalDate endDate;

    @GetMapping(value = "/v1/historyChangeResponsibleReport/getAllOrByCity",name = "Obtiene cambios de responsble por ciudad y fechas")
    public ResponseEntity<List<HistoryChangeResponsibleReport>> getAllOrByCity(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("city")) city = value;
        });
        List<HistoryChangeResponsibleReport> result;
        if(city.equals("")){
            result = repositoryHistoryChangeResponsibleReport.getAll();
        }else {
            result = repositoryHistoryChangeResponsibleReport.getByCity(city);
        }

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value ="/v1/historyChangeResponsibleReport/getHistoryChangeResponsibleReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getHistoryChangeResponsibleReport(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->  {
            if(key.equals("city")) city = value;
            if(key.equals("start-date")) startDate = LocalDate.parse(value);
            if(key.equals("end-date")) endDate = LocalDate.parse(value);
        });
        List<HistoryChangeResponsibleReport> result;
        if(city.equals("")){
            result = repositoryHistoryChangeResponsibleReport.getByAllByRageDate(startDate,endDate);
        }else {
            result = repositoryHistoryChangeResponsibleReport.getByCityAndRageDate(city, startDate, endDate);
        }
        InputStream stream = getClass().getResourceAsStream("/template-report/historyChangeResponsible/historyChangeResponsible.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("startDate",startDate);
        params.put("endDate",endDate);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,result,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

}
