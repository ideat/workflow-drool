package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.creditRequest.Charge;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.PaymentPlan;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

//import static java.time.temporal.ChronoUnit.DAYS;

public class GeneratePaymentPlan {
    private UtilPaymentPlan utilPaymentPlan = new UtilPaymentPlan();

    public List<PaymentPlan> generatePaymentPlanFixedFee(CreditRequest creditRequest) throws IOException {
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        int factor = creditRequest.getPaymentPeriod()/30;

        BigDecimal fee = BigDecimal.ZERO;
        LocalDate currentDate = creditRequest.getPaymentPlanDate();
        LocalDate nd=  utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(),currentDate,creditRequest.getPaymentPeriod(),15);
        Long dias = DAYS.between(currentDate,nd);
        if(creditRequest.getFixedDay()==0) {
            fee = utilPaymentPlan.getFixedFee(creditRequest.getAmount(), creditRequest.getRateInterest()
                    , creditRequest.getTerm() , creditRequest.getTypeTerm(), creditRequest.getPaymentPeriod()
                    ,creditRequest.getGracePeriod(),dias.intValue());
        }else{
            if(creditRequest.getFixedDay()>0) {
                fee = utilPaymentPlan.getFixedFee(creditRequest.getAmount(), creditRequest.getRateInterest()
                        , creditRequest.getTerm(), creditRequest.getTypeTerm(), creditRequest.getPaymentPeriod()
                        ,creditRequest.getGracePeriod(),dias.intValue());
            }else{
                fee = utilPaymentPlan.getFixedFeeConstanPeriod(creditRequest.getAmount(), creditRequest.getRateInterest()
                        , creditRequest.getTerm(), creditRequest.getTypeTerm(), creditRequest.getPaymentPeriod());
            }
        }

        int typeTermNumber= utilPaymentPlan.getTypeTermNumber(creditRequest.getTypeTerm());


        int term = creditRequest.getTerm()*typeTermNumber / (creditRequest.getPaymentPeriod());
        BigDecimal amount = BigDecimal.valueOf(creditRequest.getAmount());

        ObjectMapper mapper = new ObjectMapper();

        List<Charge> chargeList = mapper.readValue(creditRequest.getCharge(),new TypeReference<List<Charge>>(){});
        chargeList = chargeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
        Map<String,Double> mapCharge = getChargesValues(chargeList);
        PaymentPlan paymentPlan0 = getPaymentPlan0(creditRequest);
        paymentPlanList.add(paymentPlan0);

        int countGracePeriod = 0;
        if(creditRequest.getGracePeriod().intValue()>0) {
            countGracePeriod = (creditRequest.getGracePeriod() * 30)/creditRequest.getPaymentPeriod();
        }

        LocalDate nextDate;

        Integer numberQuota=0;
        Double itf =0.0;
//        for(int i=1;i<=countGracePeriod;i++) {
//            PaymentPlan paymentPlan = new PaymentPlan();
//            nextDate = utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(),currentDate,creditRequest.getPaymentPeriod(),10);
//            long days =  DAYS.between(currentDate,nextDate);
//            BigDecimal interest = utilPaymentPlan.getInterestFixedFee(amount,creditRequest.getRateInterest(), days,creditRequest.getPaymentPeriod());
////            Double secureCharge = utilPaymentPlan.getSecure(nextDate,currentDate,amount.doubleValue(),
////                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue();
//
//            Double secureCharge = utilPaymentPlan.getSecure2(creditRequest.getPaymentPeriod(),amount.doubleValue(),
//                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue();
//
//            Double otherCharge = mapCharge.get("SEGURO CONTRA TODO RIESGO")==null?0.0: mapCharge.get("SEGURO CONTRA TODO RIESGO").doubleValue();
//
//
//            numberQuota=i;
//            itf =  utilPaymentPlan.getItf(interest.doubleValue()+secureCharge+otherCharge
//                    , mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF"));
//            paymentPlan = getPaymentPlantGracePeriod(creditRequest.getTypeGracePeriod(), creditRequest.getAmount(),
//                    interest.doubleValue(), secureCharge, otherCharge,nextDate,numberQuota,creditRequest.getNumberRequest(),itf);
//
//            paymentPlanList.add(paymentPlan);
//            currentDate = nextDate;
//        }
//
        term=term-countGracePeriod;
        double inc =0.0;
//        List<PaymentPlan> initPaymentPlanList = new ArrayList<>(paymentPlanList);
        do {

            paymentPlanList = generatePaymentPlantWithGracePeriod(creditRequest,mapCharge);
            currentDate = paymentPlanList.get(paymentPlanList.size()-1).getPaymentDate();
            numberQuota = paymentPlanList.get(paymentPlanList.size()-1).getQuotaNumber();
            amount = BigDecimal.valueOf(creditRequest.getAmount());
            for (int i = 1; i <= term; i++) {
                nextDate = utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(), currentDate, creditRequest.getPaymentPeriod(), 15);
                long days = DAYS.between(currentDate, nextDate);
                BigDecimal interest = utilPaymentPlan.getInterestFixedFee(amount, creditRequest.getRateInterest(), days, creditRequest.getPaymentPeriod());
                if(interest.doubleValue()>fee.doubleValue()){
                    nextDate = utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(), currentDate, creditRequest.getPaymentPeriod(), 15);
                    days = DAYS.between(currentDate, nextDate);
                    interest = utilPaymentPlan.getInterestFixedFee(amount, creditRequest.getRateInterest(), days, creditRequest.getPaymentPeriod());
                }

                PaymentPlan paymentPlan = new PaymentPlan();
                paymentPlan.setId(UUID.randomUUID());
                paymentPlan.setPaymentDate(nextDate);
                paymentPlan.setQuotaNumber(numberQuota + i);
                paymentPlan.setInterest(interest.doubleValue());
                paymentPlan.setNumberRequest(creditRequest.getNumberRequest());
//                paymentPlan.setSecureCharge(utilPaymentPlan.getSecure(nextDate, currentDate, amount.doubleValue(),
//                        mapCharge.get("SEGURO DESGRAVAMEN") == null ? 0.0 : mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

                paymentPlan.setSecureCharge( utilPaymentPlan.getSecure2(creditRequest.getPaymentPeriod(),amount.doubleValue(),
                        mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

                paymentPlan.setOtherCharge(mapCharge.get("SEGURO CONTRA TODO RIESGO") == null ? 0.0 : mapCharge.get("SEGURO CONTRA TODO RIESGO").doubleValue()*factor);

                BigDecimal amort = fee.subtract(interest);

                amount = amount.subtract(amort);
                BigDecimal feepay = new BigDecimal(0);

                if (term == i) {
                    paymentPlan.setCapital(amort.setScale(2, RoundingMode.UP).doubleValue() + amount.doubleValue());
                    paymentPlan.setResidue(0.0);
                    feepay = interest.setScale(2, RoundingMode.UP)
                            .add(amort.setScale(2, RoundingMode.UP).add(amount))
                            .add(new BigDecimal(paymentPlan.getSecureCharge()))
                            .add(new BigDecimal(paymentPlan.getOtherCharge()));
                    itf = utilPaymentPlan.getItf(feepay.doubleValue(), mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF").doubleValue(),creditRequest.getCurrency());
                    feepay = feepay.add(BigDecimal.valueOf(itf));

                } else {
                    paymentPlan.setResidue(amount.doubleValue());
                    paymentPlan.setCapital(amort.doubleValue());

                    feepay = interest.setScale(2, RoundingMode.UP)
                            .add(amort.setScale(2, RoundingMode.UP))
                            .add(new BigDecimal(paymentPlan.getSecureCharge()))
                            .add(new BigDecimal(paymentPlan.getOtherCharge()));
                    itf = utilPaymentPlan.getItf(feepay.doubleValue(), mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF").doubleValue(),creditRequest.getCurrency());
                    feepay = feepay.add(BigDecimal.valueOf(itf));
                }

                paymentPlan.setFee(feepay.doubleValue());
                paymentPlan.setItf(itf);
                paymentPlanList.add(paymentPlan);
                currentDate = nextDate;
            }
            fee = fee.add(BigDecimal.valueOf(inc));
            inc=inc+0.01;
        }while(UtilPaymentPlan.correctPaymentPlan(paymentPlanList,fee.doubleValue()));

        return paymentPlanList;
    }

    private List<PaymentPlan> generatePaymentPlantWithGracePeriod(CreditRequest creditRequest,  Map<String,Double> mapCharge){
        int factor = creditRequest.getPaymentPeriod()/30;
        PaymentPlan paymentPlan0 = getPaymentPlan0(creditRequest);
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        int countGracePeriod = 0;
        BigDecimal amount = BigDecimal.valueOf(creditRequest.getAmount());
        LocalDate currentDate = creditRequest.getPaymentPlanDate();
        if(creditRequest.getGracePeriod().intValue()>0) {
            countGracePeriod = (creditRequest.getGracePeriod() * 30)/creditRequest.getPaymentPeriod();
        }
        LocalDate nextDate;
        paymentPlanList.add(paymentPlan0);
        Integer numberQuota=0;
        double itf=0.0;
        for(int i=1;i<=countGracePeriod;i++) {
            PaymentPlan paymentPlan = new PaymentPlan();
            nextDate = utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(),currentDate,creditRequest.getPaymentPeriod(),15);
            long days =  DAYS.between(currentDate,nextDate);
            BigDecimal interest = BigDecimal.ZERO;
            if(creditRequest.getTypeTerm().equals("FIJA")) {
                interest = utilPaymentPlan.getInterestFixedFee(amount, creditRequest.getRateInterest(), days, creditRequest.getPaymentPeriod());
            }else{
                int periodYear = 360/creditRequest.getPaymentPeriod();
                interest = utilPaymentPlan.getInterestVariableFee(amount,creditRequest.getRateInterest(), periodYear);
            }
            //            Double secureCharge = utilPaymentPlan.getSecure(nextDate,currentDate,amount.doubleValue(),
//                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue();

            Double secureCharge =  utilPaymentPlan.getSecure2(creditRequest.getPaymentPeriod(),amount.doubleValue(),
                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue();

            Double otherCharge = mapCharge.get("SEGURO CONTRA TODO RIESGO")==null?0.0: mapCharge.get("SEGURO CONTRA TODO RIESGO").doubleValue()*factor;

            numberQuota=i;
            itf =  utilPaymentPlan.getItf(interest.doubleValue()+secureCharge+otherCharge
                    , mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF"),creditRequest.getCurrency());
            paymentPlan = getPaymentPlantGracePeriod(creditRequest.getTypeGracePeriod(), creditRequest.getAmount(),
                    interest.doubleValue(), secureCharge, otherCharge,nextDate,numberQuota,creditRequest.getNumberRequest(),itf);

            paymentPlanList.add(paymentPlan);
            currentDate = nextDate;
        }
        return paymentPlanList;
    }

    public List<PaymentPlan> generatePaymentPlanVariableFee(CreditRequest creditRequest) throws IOException {
        int factor = creditRequest.getPaymentPeriod()/30;
         List<PaymentPlan> paymentPlanList = new ArrayList<>();
        UtilPaymentPlan utilPaymentPlan = new UtilPaymentPlan();

        BigDecimal amount = BigDecimal.valueOf(creditRequest.getAmount());
        LocalDate currentDate = creditRequest.getPaymentPlanDate();
        BigDecimal amortization = utilPaymentPlan.getAmortizationVariableFee(amount,creditRequest.getRateInterest(),
                creditRequest.getTerm(),creditRequest.getTypeTerm(),creditRequest.getPaymentPeriod());
        int typeTermNumber= utilPaymentPlan.getTypeTermNumber(creditRequest.getTypeTerm());

        int term = creditRequest.getTerm()*typeTermNumber / (creditRequest.getPaymentPeriod());
        int periodYear = 360/creditRequest.getPaymentPeriod();
        ObjectMapper mapper = new ObjectMapper();
        List<Charge> chargeList = mapper.readValue(creditRequest.getCharge(),new TypeReference<List<Charge>>(){});
        chargeList = chargeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
        Map<String,Double> mapCharge = getChargesValues(chargeList);
        PaymentPlan paymentPlan0 = getPaymentPlan0(creditRequest);

        paymentPlanList.add(paymentPlan0);

        int countGracePeriod = 0;
        if(creditRequest.getGracePeriod().intValue()>0) {
            countGracePeriod = (creditRequest.getGracePeriod() * 30)/creditRequest.getPaymentPeriod();
        }

        term=term-countGracePeriod;

        Integer numberQuota=0;
        paymentPlanList = generatePaymentPlantWithGracePeriod(creditRequest,mapCharge);
        currentDate = paymentPlanList.get(paymentPlanList.size()-1).getPaymentDate();
        numberQuota = paymentPlanList.get(paymentPlanList.size()-1).getQuotaNumber();
        amount = BigDecimal.valueOf(creditRequest.getAmount());
        double itf=0.0;
        for(int i=1;i<=term;i++){
            LocalDate nextDate = utilPaymentPlan.nextPaymentDate(creditRequest.getFixedDay(),currentDate,creditRequest.getPaymentPeriod(),20);
//            long days = DAYS.between(currentDate,nextDate);
            BigDecimal interest = utilPaymentPlan.getInterestVariableFee(amount,creditRequest.getRateInterest(),periodYear);


            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setId(UUID.randomUUID());
            paymentPlan.setNumberRequest(creditRequest.getNumberRequest());
            paymentPlan.setQuotaNumber(i+numberQuota);
            paymentPlan.setInterest(interest.doubleValue());
//            paymentPlan.setSecureCharge(utilPaymentPlan.getSecure(nextDate,currentDate,amount.doubleValue(),
//                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

            paymentPlan.setSecureCharge( utilPaymentPlan.getSecure2(creditRequest.getPaymentPeriod(),amount.doubleValue(),
                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

            if(term==i) {
                amortization = amount;
                amount = amount.subtract(amortization);
            }else{
                amount = amount.subtract(amortization);
            }

            ///no entiendo
            paymentPlan.setOtherCharge(mapCharge.get("SEGURO CONTRA TODO RIESGO")==null?0.0: mapCharge.get("SEGURO CONTRA TODO RIESGO").doubleValue()*factor);

            paymentPlan.setPaymentDate(nextDate);
            BigDecimal feepay = new BigDecimal(0);

            if(term==i){
                paymentPlan.setCapital(amortization.doubleValue()+amount.doubleValue());
                paymentPlan.setResidue(0.0);
                feepay = interest.add(amortization).add(amount).add(new BigDecimal(paymentPlan.getSecureCharge())).add(new BigDecimal(paymentPlan.getOtherCharge()));
                itf = utilPaymentPlan.getItf(feepay.doubleValue(), mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF").doubleValue(),creditRequest.getCurrency());
                feepay = feepay.add(BigDecimal.valueOf(itf));
            }else{
                paymentPlan.setCapital(amortization.doubleValue());
                paymentPlan.setResidue(amount.doubleValue());
                feepay = interest.add(amortization).add(new BigDecimal(paymentPlan.getSecureCharge())).add(new BigDecimal(paymentPlan.getOtherCharge()));
                itf = utilPaymentPlan.getItf(feepay.doubleValue(), mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF").doubleValue(),creditRequest.getCurrency());
                feepay = feepay.add(BigDecimal.valueOf(itf));
            }

            paymentPlan.setFee(feepay.setScale(2,RoundingMode.UP).doubleValue());
            paymentPlan.setItf(itf);
            paymentPlanList.add(paymentPlan);

            currentDate = nextDate;
        }

        return paymentPlanList;

    }


    public List<PaymentPlan> generatePaymentPlanTermFixed(CreditRequest creditRequest) throws IOException {
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        UtilPaymentPlan utilPaymentPlan = new UtilPaymentPlan();

        BigDecimal amount = BigDecimal.valueOf(creditRequest.getAmount());
        LocalDate currentDate = creditRequest.getPaymentPlanDate();
//        BigDecimal amortization = utilPaymentPlan.getAmortizationVariableFee(amount,creditRequest.getRateInterest(),
//                creditRequest.getTerm(),creditRequest.getTypeTerm(),creditRequest.getPaymentPeriod());
        int typeTermNumber= utilPaymentPlan.getTypeTermNumber(creditRequest.getTypeTerm());

        int term = 1;
        int periodYear = 1;// 360/creditRequest.getPaymentPeriod();
        ObjectMapper mapper = new ObjectMapper();
        List<Charge> chargeList = mapper.readValue(creditRequest.getCharge(),new TypeReference<List<Charge>>(){});
        chargeList = chargeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
        Map<String,Double> mapCharge = getChargesValues(chargeList);
        PaymentPlan paymentPlan0 = getPaymentPlan0(creditRequest);

        Long paymentPeriod =  DAYS.between(currentDate,creditRequest.getPaymentPlanEndDate());

        paymentPlanList.add(paymentPlan0);
//        for(int i=1;i<=term;i++){
            LocalDate nextDate = utilPaymentPlan.nextPaymentDate(0,currentDate,paymentPeriod.intValue(),20);
//            long days = DAYS.between(currentDate,nextDate);
            BigDecimal interest = utilPaymentPlan.getInterestVariableFee(amount,creditRequest.getRateInterest(),periodYear);

            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setId(UUID.randomUUID());
            paymentPlan.setNumberRequest(creditRequest.getNumberRequest());
            paymentPlan.setQuotaNumber(1);
            paymentPlan.setInterest(interest.doubleValue());
//            paymentPlan.setSecureCharge(utilPaymentPlan.getSecure(nextDate,currentDate,amount.doubleValue(),
//                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

            paymentPlan.setSecureCharge( utilPaymentPlan.getSecure2(creditRequest.getPaymentPeriod(),amount.doubleValue(),
                    mapCharge.get("SEGURO DESGRAVAMEN")==null?0.0:mapCharge.get("SEGURO DESGRAVAMEN")).doubleValue());

            paymentPlan.setOtherCharge(utilPaymentPlan.getSecure(nextDate,currentDate,amount.doubleValue(),
                    mapCharge.get("SEGURO CONTRA TODO RIESGO")==null?0.0: mapCharge.get("SEGURO CONTRA TODO RIESGO")).doubleValue());

            paymentPlan.setPaymentDate(nextDate);
            BigDecimal feepay = new BigDecimal(0);


            paymentPlan.setCapital(amount.doubleValue());
            paymentPlan.setResidue(0.0);
            feepay = interest.add(amount).add(new BigDecimal(paymentPlan.getSecureCharge())).add(new BigDecimal(paymentPlan.getOtherCharge()));

            double itf = utilPaymentPlan.getItf(feepay.doubleValue(), mapCharge.get("ITF")==null?0.0:mapCharge.get("ITF").doubleValue(),creditRequest.getCurrency());
            paymentPlan.setItf(itf);

            feepay = feepay.add(BigDecimal.valueOf(itf));

            paymentPlan.setFee(feepay.setScale(2,RoundingMode.HALF_UP).doubleValue());

            paymentPlanList.add(paymentPlan);

//        }

        return paymentPlanList;

    }


    private PaymentPlan getPaymentPlan0(CreditRequest creditRequest) {
        PaymentPlan paymentPlan0 = new PaymentPlan();
        paymentPlan0.setId(UUID.randomUUID());
        paymentPlan0.setQuotaNumber(0);
        paymentPlan0.setFee(0.0);
        paymentPlan0.setCapital(0.0);
        paymentPlan0.setInterest(0.0);
        paymentPlan0.setOtherCharge(0.0);
        paymentPlan0.setSecureCharge(0.0);
        paymentPlan0.setPaymentDate(creditRequest.getPaymentPlanDate());
        paymentPlan0.setResidue(creditRequest.getAmount());
        paymentPlan0.setNumberRequest(creditRequest.getNumberRequest());
        paymentPlan0.setItf(0.0);
        return paymentPlan0;
    }

    private PaymentPlan getPaymentPlantGracePeriod(String typeGracePeriod, Double amount, Double interest, Double secureCharge,
                                                Double otherCharges, LocalDate paymentDate, Integer numberQuota, Integer numberRequest, Double itf ){
        PaymentPlan paymentPlan0 = new PaymentPlan();
        paymentPlan0.setQuotaNumber(numberQuota);
        paymentPlan0.setId(UUID.randomUUID());
        if(typeGracePeriod.equals("CAPITAL")){
            paymentPlan0.setFee(secureCharge+otherCharges+interest);
            paymentPlan0.setInterest(interest.doubleValue());
            paymentPlan0.setOtherCharge(otherCharges);
            paymentPlan0.setSecureCharge(secureCharge);
            paymentPlan0.setCapital(0.0);
            paymentPlan0.setPaymentDate(paymentDate);
            paymentPlan0.setResidue(amount);
            paymentPlan0.setNumberRequest(numberRequest);
            paymentPlan0.setItf(itf);
        }else{
            paymentPlan0.setFee(0.0);
            paymentPlan0.setInterest(0.0);
            paymentPlan0.setOtherCharge(0.0);
            paymentPlan0.setSecureCharge(0.0);
            paymentPlan0.setCapital(0.0);
            paymentPlan0.setPaymentDate(paymentDate);
            paymentPlan0.setResidue(amount);
            paymentPlan0.setNumberRequest(numberRequest);
            paymentPlan0.setItf(0.0);
        }
        return paymentPlan0;
    }

    private Map<String,Double> getChargesValues(List<Charge> chargeList){
        Map<String,Double> map = new HashMap<>();
        for(Charge c : chargeList){
            map.put(c.getName(),c.getValue());
        }
        return map;
    }


}
