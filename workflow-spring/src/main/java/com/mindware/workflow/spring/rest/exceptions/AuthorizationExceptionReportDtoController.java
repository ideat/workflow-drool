package com.mindware.workflow.spring.rest.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizationExceptionReportDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizationExceptionReportDto;
import com.mindware.workflow.core.service.task.CreateExceptionAuthorizationReportDto;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class AuthorizationExceptionReportDtoController {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryAuthorizationExceptionReportDto repository;

    private Integer numberRequest;
    private String typeException;

    @GetMapping(value = "/v1/authorizationExceptionReport/getByNumberRequest",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getByNumberRequest(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) -> {
            if (key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if (key.equals("type-exception")) typeException = value;
        });

        List<AuthorizationExceptionReportDto> collection = repository.getByNumberRequest(numberRequest,typeException);

        List<AuthorizationExceptionReportDto> result = CreateExceptionAuthorizationReportDto.generate(collection);

        InputStream stream = getClass().getResourceAsStream("/template-report/authorizationException/authorizationException.jrxml");

        String pathLogo = getClass().getResource("/template-report/img/logo.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,result,params);
        InputStream is = new ByteArrayInputStream(b);
        return IOUtils.toByteArray(is);
    }
}
