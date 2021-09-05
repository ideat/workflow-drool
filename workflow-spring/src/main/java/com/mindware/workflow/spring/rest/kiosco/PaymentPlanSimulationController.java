package com.mindware.workflow.spring.rest.kiosco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.creditRequest.Charge;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.kiosco.PaymentPlanSimulation;
import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.task.GeneratePaymentPlan;
import com.mindware.workflow.core.service.task.UtilPaymentPlan;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/media"})
public class PaymentPlanSimulationController {
    private Double teac = 0.0;
    private Double tea = 0.0;
    private PaymentPlanSimulation paymentPlanSimulation;
    private String paymentPlanSimulationStr;

    @GetMapping(value = "/kiosco/paymentplansimulation/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getPaymentPlanReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {

        headers.forEach((key,value) ->{
            if(key.equals("paymentplansimulation")) paymentPlanSimulationStr = value;
        });

        ObjectMapper mapper = new ObjectMapper();
        paymentPlanSimulation = mapper.readValue(paymentPlanSimulationStr, new TypeReference<PaymentPlanSimulation>(){});

        GeneratePaymentPlan generatePaymentPlan = new GeneratePaymentPlan();
        List<PaymentPlan> paymentPlanList = new ArrayList<>();

        CreditRequest creditRequest = createCreditRequest(paymentPlanSimulation);

        if(creditRequest.getTypeFee().equals("FIJA")){
            paymentPlanList = generatePaymentPlan.generatePaymentPlanFixedFee(creditRequest,"KIOSCO");
        }else if(creditRequest.getTypeFee().equals("VARIABLE")){
            paymentPlanList = generatePaymentPlan.generatePaymentPlanVariableFee(creditRequest);
        }else{
            paymentPlanList = generatePaymentPlan.generatePaymentPlanTermFixed(creditRequest);
        }
        List<PaymentPlanDto> paymentPlantDtoList = createPaymentPlanDto(paymentPlanSimulation,paymentPlanList);

        double[] payments = UtilPaymentPlan.getListFee(paymentPlanList,paymentPlantDtoList.get(0).getAmount());
        teac = 0.0;
        tea = 0.0;

        double factor = creditRequest.getPaymentPeriod()/30.0;

        teac = UtilPaymentPlan.irr3(payments, 0.0001d) * 11.8356;

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

    private CreditRequest createCreditRequest(PaymentPlanSimulation paymentPlanSimulation){
        CreditRequest creditRequest = new CreditRequest();

        List<Charge> chargeList = new ArrayList<>();
        Charge c1 = new Charge();
        c1.setId(UUID.randomUUID());
        c1.setName("SEGURO DESGRAVAMEN");
        c1.setSelected(true);
        c1.setValue(paymentPlanSimulation.getSecure());

        Charge c2 = new Charge();
        c2.setId(UUID.randomUUID());
        c2.setName("SEGURO CONTRA TODO RIESGO");
        c2.setSelected(true);
        c2.setValue(paymentPlanSimulation.getAllRisk());

        Charge c3 = new Charge();
        c3.setId(UUID.randomUUID());
        c3.setName("ITF");
        c3.setSelected(true);
        c3.setValue(0.0);

        chargeList.add(c1);
        chargeList.add(c2);
        chargeList.add(c3);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String charges = mapper.writeValueAsString(chargeList);
            creditRequest.setCharge(charges);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        creditRequest.setPaymentPlanDate(paymentPlanSimulation.getPaymentPlanDate());
        creditRequest.setFixedDay(paymentPlanSimulation.getFixedDay());
        creditRequest.setPaymentPeriod(paymentPlanSimulation.getPaymentPeriod());
        creditRequest.setAmount(paymentPlanSimulation.getAmount());
        creditRequest.setRateInterest(paymentPlanSimulation.getRate());
        creditRequest.setTerm(paymentPlanSimulation.getTerm());
        creditRequest.setTypeTerm(paymentPlanSimulation.getTypeTerm());
        creditRequest.setPaymentPeriod(paymentPlanSimulation.getPaymentPeriod());
        creditRequest.setGracePeriod(0);
        creditRequest.setTypeFee(paymentPlanSimulation.getTypeFee());
        creditRequest.setCurrency("BS");

        return creditRequest;
    }

    private List<PaymentPlanDto> createPaymentPlanDto(PaymentPlanSimulation paymentPlanSimulation, List<PaymentPlan> paymentPlanList){
        List<PaymentPlanDto> paymentPlanDtoList = new ArrayList<>();

        for(PaymentPlan p: paymentPlanList){
            PaymentPlanDto paymentPlanDto = new PaymentPlanDto();
            paymentPlanDto.setFullName(paymentPlanSimulation.getFullName());
            paymentPlanDto.setNumberRequest(0);
            paymentPlanDto.setRequestDate(new Date());
            paymentPlanDto.setAmount(paymentPlanSimulation.getAmount());
            paymentPlanDto.setFeeNumber(p.getQuotaNumber());
            paymentPlanDto.setTypeFee(paymentPlanSimulation.getTypeFee());
            paymentPlanDto.setCurrency("BS");
            paymentPlanDto.setRateInterest(paymentPlanSimulation.getRate());
            paymentPlanDto.setPaymentPeriod(paymentPlanSimulation.getPaymentPeriod());
            paymentPlanDto.setQuotaNumber(p.getQuotaNumber());
            paymentPlanDto.setPaymentDate(Date.from(p.getPaymentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            paymentPlanDto.setCapital(p.getCapital());
            paymentPlanDto.setInterest(p.getInterest());
            paymentPlanDto.setSecureCharge(p.getSecureCharge());
            paymentPlanDto.setOtherCharge(p.getOtherCharge());
            paymentPlanDto.setFee(p.getFee());
            paymentPlanDto.setResidue(p.getResidue());
            paymentPlanDto.setTeac(teac);
            paymentPlanDto.setTea(tea);
            paymentPlanDto.setItf(p.getItf());
            paymentPlanDtoList.add(paymentPlanDto);
        }


        return paymentPlanDtoList;
    }
}
