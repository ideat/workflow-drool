package com.mindware.workflow.core.service.task;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.PaymentPlan;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeneretePaymentPlanTest {
    Double capital = 0.0;



    @Test
    public void testGeneratePaymentPlanFixedFee() throws IOException {
//        GeneratePaymentPlan generatePaymentPlan = new GeneratePaymentPlan();
//        List<PaymentPlan> paymentPlanList = new ArrayList<>();
//        CreditRequest creditRequest = new CreditRequest();
//        creditRequest.setAmount(4200.0);
//        creditRequest.setRateInterest(20.0);
//        creditRequest.setPaymentPeriod(30);
//        creditRequest.setFixedDay(0);
//        creditRequest.setNumberRequest(1);
//        creditRequest.setTerm(12);
//        creditRequest.setTypeTerm("MES");
//        creditRequest.setRequestDate(LocalDate.of(2007,8,30));
//        creditRequest.setPaymentPlanDate(LocalDate.of(2020,2,8));
//        creditRequest.setCharge("[{\"id\": \"e1d16613-7931-4ca4-93ec-ba800916e5d6\", \"name\": \"SEGURO CONTRA INCENDIO\", \"value\": 0.036, \"selected\": true}, {\"id\": \"5fdf4679-3153-41cc-ae02-4f5b981f4c0f\", \"name\": \"SEGURO DESGRAVAMEN\", \"value\": 0.96, \"selected\": true}]");
//
//        paymentPlanList = generatePaymentPlan.generatePaymentPlanFixedFee(creditRequest);
//
//        paymentPlanList.forEach(e ->  {
//            capital = capital + e.getCapital();
//
//        });
//        assertEquals(capital,creditRequest.getAmount());

    }

    @Test
    public void testGeneratePaymentPlanVariableFee() throws IOException {
//        GeneratePaymentPlan generatePaymentPlan = new GeneratePaymentPlan();
//        List<PaymentPlan> paymentPlanList = new ArrayList<>();
//        CreditRequest creditRequest = new CreditRequest();
//        creditRequest.setAmount(24000.0);
//        creditRequest.setRateInterest(13.0);
//        creditRequest.setPaymentPeriod(60);
//        creditRequest.setFixedDay(5);
//        creditRequest.setNumberRequest(18);
//        creditRequest.setTerm(12);
//        creditRequest.setTypeTerm("MES");
//        creditRequest.setRequestDate(LocalDate.of(2007,8,30));
//        creditRequest.setPaymentPlanDate(LocalDate.of(2020,2,8));
//        creditRequest.setCharge("[{\"id\": \"e1d16613-7931-4ca4-93ec-ba800916e5d6\", \"name\": \"SEGURO CONTRA INCENDIO\", \"value\": 0.036, \"selected\": true}, {\"id\": \"5fdf4679-3153-41cc-ae02-4f5b981f4c0f\", \"name\": \"SEGURO DESGRAVAMEN\", \"value\": 0.96, \"selected\": true}]");
//
//        paymentPlanList = generatePaymentPlan.generatePaymentPlanVariableFee(creditRequest);
//
//        paymentPlanList.forEach(e ->  {
//            capital = capital + e.getCapital();
//
//        });
//        assertEquals(capital,creditRequest.getAmount());


    }
}
