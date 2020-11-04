package com.mindware.workflow.spring.rest.cashFlow;

import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlow;
import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestReportDto;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;
import com.mindware.workflow.core.service.task.CreateCashFlowInputReport;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CashFlowCreditRequestReportController {
    @Autowired
    RepositoryCashFlow repositoryCashFlow;

    @Autowired
    RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto;

    private Integer numberRequest;


    @GetMapping(value = "/v1/cashFlowReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getCashFlowReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        CashFlowCreditRequestReportDto cashFlowCreditRequestReportDto = new CashFlowCreditRequestReportDto();
        headers.forEach((key,value) -> {
            if (key.equals("number-request")) numberRequest = Integer.parseInt(value);
        });

        CreditRequestApplicantdto  creditRequestApplicantdto = repositoryCreditRequestApplicantDto.getByNumberRequestTypeRelation(numberRequest,"deudor").get(0);
        CashFlow cashFlow = repositoryCashFlow.getByNumberRequest(numberRequest).get();

        CreateCashFlowInputReport createCashFlowInputReport = new CreateCashFlowInputReport();
        cashFlowCreditRequestReportDto = createCashFlowInputReport.createInputReport(cashFlow,creditRequestApplicantdto);

        InputStream stream = getClass().getResourceAsStream("/template-report/cashFlow/cashFlow.jrxml");
        String pathLogo = "template-report/cashFlow/";
        String pathSubreport = "template-report/cashFlow/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<CashFlowCreditRequestReportDto> collection = new ArrayList<>();
        collection.add(cashFlowCreditRequestReportDto);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

}

