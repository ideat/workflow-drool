package com.mindware.workflow.spring.rest.paymentPlan;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.task.GeneratePaymentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class PaymentPlanController {
    private Integer numberRequest;

    @Autowired
    RepositoryPaymentPlan repository;

    @PostMapping(value = "/v1/paymentplan/add", name = "Crear plan de pagos")
    ResponseEntity<List<PaymentPlan>> create(@RequestBody CreditRequest creditRequest, HttpServletRequest request) throws IOException {
        GeneratePaymentPlan generatePaymentPlan = new GeneratePaymentPlan();
        List<PaymentPlan> paymentPlanList = new ArrayList<>();

        repository.delete(creditRequest.getNumberRequest());
        if(creditRequest.getTypeFee().equals("FIJA")){
            paymentPlanList = generatePaymentPlan.generatePaymentPlanFixedFee(creditRequest);
        }else if(creditRequest.getTypeFee().equals("VARIABLE")){
            paymentPlanList = generatePaymentPlan.generatePaymentPlanVariableFee(creditRequest);
        }else{
            paymentPlanList = generatePaymentPlan.generatePaymentPlanTermFixed(creditRequest);
        }

        if(paymentPlanList.size()>0){
            for(PaymentPlan p : paymentPlanList){
                repository.add(p);
            }

        }
        return new ResponseEntity<>(paymentPlanList, HttpStatus.CREATED);
    }

    @GetMapping(value = "/v1/paymentPlan/getByNumberRequest/{numberrequest}", name = "Plan de pagos solicitud")
    ResponseEntity<Collection<PaymentPlan>> getByNumberRquest(@PathVariable("numberrequest") Integer numberRequest){
        List<PaymentPlan> paymentPlanList = repository.getPaymentPlanByNumberRequest(numberRequest);
        return new ResponseEntity<>(paymentPlanList,HttpStatus.OK);
    }
}
