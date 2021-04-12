package com.mindware.workflow.spring.rest.paymentPlan;

import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import com.mindware.workflow.core.service.task.UtilPaymentPlan;
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
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping(value = "/rest", produces = {"application/media"})
public class PaymentPlanDtoController {

    private Double teac;
    private Double  tea;

    @Autowired
    RepositoryPaymentPlanDto repository;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @GetMapping(value = "/v1/paymentplanreport/{numberrequest}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getPaymentPlanReport(@PathVariable int numberrequest) throws JRException, IOException {
        List<PaymentPlanDto> paymentPlantDtoList = new ArrayList<>();
        paymentPlantDtoList = repository.getDataReportPaymentPlant(numberrequest);
        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberrequest);
        double[] payments = UtilPaymentPlan.getListFee(paymentPlanList,paymentPlantDtoList.get(0).getAmount());
//        Date[] datePays = UtilPaymentPlan.datePayments(paymentPlanList);
        teac = 0.0;
        tea = 0.0;

        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberrequest).get();
        double factor = creditRequest.getPaymentPeriod()/30.0;
        if(creditRequest.getTypeFee().equals("PLAZO FIJO")){

            teac = UtilPaymentPlan.irr3(payments, 0.0001d)  * 11.8356 ; //estaba sin  * 11.8356

            Long days = DAYS.between(creditRequest.getPaymentPlanDate(),creditRequest.getPaymentPlanEndDate());
            factor = days.intValue()/30.0;

            paymentPlantDtoList.stream().forEach(p -> p.setPaymentPeriod(days.intValue()));
        }else {
            teac = UtilPaymentPlan.irr3(payments, 0.0001d) * 11.8356;//11.6615;//11.8356;
        }
        teac = teac/factor;
        int numberFee = paymentPlantDtoList.size()-1;
        paymentPlantDtoList.stream().forEach(p -> {
            p.setFeeNumber(numberFee);
            p.setTeac(teac);
            p.setTea(tea);
        });


        InputStream stream = getClass().getResourceAsStream(creditRequest.getCurrency().equals("Bs.")
                ?"/template-report/paymentPlan.jrxml":"/template-report/paymentPlanSus.jrxml");

        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,paymentPlantDtoList,params);
        InputStream is = new ByteArrayInputStream(b);
//        final StreamResource streamResource = new StreamResource("paymentPlan.pdf" , () -> is);

        return IOUtils.toByteArray(is);
    }
}
