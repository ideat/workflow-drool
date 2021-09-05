package com.mindware.workflow.core.service.task;

import com.mindware.workflow.core.entity.PaymentPlan;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class UtilPaymentPlan {

    public LocalDate nextPaymentDate(int fixedDay, LocalDate initDate, int paymentPeriod, int minDays, int maxDays){

        int period = paymentPeriod/30;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(initDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Calendar aux = (Calendar) calendar.clone();

//
        long days;
        if(fixedDay>0) {
            calendar.add(Calendar.MONTH,period);
            calendar = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),fixedDay);
            days = DAYS.between(aux.toInstant(),calendar.toInstant());
            if(maxDays < (days - paymentPeriod)){
                calendar.add(Calendar.MONTH,(period*-1));
            }else {
                if (days <= minDays) {
                    calendar.add(Calendar.MONTH, 1);
                }
            }
        }else{
            calendar.add(Calendar.DATE,paymentPeriod);
        }

        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            calendar.add(Calendar.DATE,1);
        }
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
    }

    public Double getTea(Double rate, Double otherCharge, Double bankFees,  double paymentPeriod){
        Double r = rate/(paymentPeriod/30)/100;
        Double n = Math.pow(1+((r+otherCharge)*(paymentPeriod/360)),(360/paymentPeriod));
        Double d = 1 - bankFees;

        return (n/d)-1;
    }

    public BigDecimal getSecure(LocalDate currentDate, LocalDate previousDate, Double amount, Double rateSecure ){

        if(rateSecure==0.0){
            return new BigDecimal(0);
        }
        long days = DAYS.between(previousDate,currentDate);

        return   BigDecimal.valueOf(amount*rateSecure*days/36000).setScale(2,RoundingMode.UP); //Math.round((amount*rateSecure*12*days/36000) * 100d)/100d ;
    }

    public BigDecimal getSecure2(Integer paymentPeriod, Double amount, Double rateSecure ){

        if(rateSecure==0.0){
            return new BigDecimal(0);
        }
        return   BigDecimal.valueOf(amount*rateSecure*paymentPeriod/36000).setScale(2,RoundingMode.UP); //Math.round((amount*rateSecure*12*days/36000) * 100d)/100d ;
    }

    public BigDecimal getFixedFee2(Double amount, Double rate, int term, String typeTerm
            , int paymentPeriod, LocalDate initDate, int fixedDay,int minDays, int maxDays){
        Double termd = new Double(term);
        int typeTermNumber = getTypeTermNumber(typeTerm);
        termd = termd*typeTermNumber;
        int feeNumber = (int) (termd/paymentPeriod);
        LocalDate endDate = initDate;
        for(int i=1;i<=feeNumber;i++){
            endDate = nextPaymentDate(fixedDay,endDate,paymentPeriod,minDays, maxDays);
        }
        long days = DAYS.between(initDate,endDate);

        Double step1 = (rate/36000)*(days/feeNumber);
        Double step2 = Math.pow (1+step1,feeNumber);
        Double step3 = step2 -1;
        Double step4 = step1 * step2;
        Double step5 = step3/step4;
        Double fee= amount / step5;

        return new BigDecimal(fee);

    }

    public BigDecimal getFixedFee(Double amount, Double rate,  int term, String typeTerm, int paymentPeriod,
                                  int gracePeriod, int days){

//        Double termd = new Double(term);
//
//        int typeTermNumber = getTypeTermNumber(typeTerm);
//        termd = (termd*typeTermNumber)-(gracePeriod*30);
//        int feeNumber = (int) (termd/paymentPeriod);
//
//        Double x = BigDecimal.valueOf(rate/36000).setScale(6,RoundingMode.DOWN).doubleValue();
//        BigDecimal ratePeriod = BigDecimal.valueOf(x*(termd/feeNumber)).setScale(4,RoundingMode.UP);  //(rate/(12/(paymentPeriod/30)))/100;
////        BigDecimal ratePeriod = BigDecimal.valueOf(x*(days)).setScale(4,RoundingMode.UP);  //(rate/(12/(paymentPeriod/30)))/100;
//        BigDecimal a = ratePeriod.add(new BigDecimal(1)).pow(feeNumber).setScale(4,RoundingMode.UP);  // Math.pow(ratePeriod*(1+ratePeriod),term);
//        BigDecimal b = a.subtract(new BigDecimal(1)); //Math.pow((1+ratePeriod),term) - 1;
//        BigDecimal c = ratePeriod.multiply(a).setScale(4,RoundingMode.UP);
//        BigDecimal d = b.divide(c,10,BigDecimal.ROUND_UP).setScale(2,RoundingMode.UP);

//        Double r = (360.0*12.0/365.0)*1000.0/1000.0;

        int typeTermNumber = getTypeTermNumber(typeTerm);
        Double termd = new Double(term);
        termd = (termd*typeTermNumber)-(gracePeriod*30);
        int feeNumber = (int) (termd/paymentPeriod);

        BigDecimal r =  BigDecimal.valueOf(360.0*12.0/365.0).setScale(5,RoundingMode.UP);  //360*12/365

        BigDecimal raten = BigDecimal.valueOf(rate/r.doubleValue()/100.0).setScale(6,RoundingMode.UP); //r / rate

        BigDecimal p1 = BigDecimal.valueOf(1).add(raten); //1+raten

        BigDecimal pot1 = p1.pow(feeNumber);
        BigDecimal pot = BigDecimal.valueOf(1.0/pot1.doubleValue()).setScale(5,RoundingMode.DOWN);
        BigDecimal den = BigDecimal.valueOf(1).subtract(pot).setScale(6,RoundingMode.DOWN);
        BigDecimal res = den.divide(raten,RoundingMode.UP).setScale(2,RoundingMode.DOWN);
        BigDecimal cuo = BigDecimal.valueOf(amount).divide(res,RoundingMode.UP);



        return cuo.setScale(2,RoundingMode.UP);
//        return BigDecimal.valueOf(amount).divide(d,10,BigDecimal.ROUND_UP).setScale(2, RoundingMode.UP);
    }


    public BigDecimal getFixedFeeConstanPeriod(Double amount, Double rate,  int term, String typeTerm, int paymentPeriod ){

        Double termd = new Double(term);

        int typeTermNumber = getTypeTermNumber(typeTerm);
        termd = termd*typeTermNumber;
        int feeNumber = (int) (termd/paymentPeriod);
//35672.6
        BigDecimal ratePeriod = BigDecimal.valueOf((rate/36000)*(termd/feeNumber)).setScale(5,RoundingMode.DOWN);  //(rate/(12/(paymentPeriod/30)))/100;
        BigDecimal a = ratePeriod.add(new BigDecimal(1)).pow(feeNumber);  // Math.pow(ratePeriod*(1+ratePeriod),term);
        BigDecimal b = a.subtract(new BigDecimal(1)); //Math.pow((1+ratePeriod),term) - 1;
        BigDecimal c = ratePeriod.multiply(a);
        BigDecimal d = b.divide(c,10,BigDecimal.ROUND_DOWN);

        return BigDecimal.valueOf(amount).divide(d,10,BigDecimal.ROUND_DOWN).setScale(2, RoundingMode.UP);
    }

    public BigDecimal getAmortizationVariableFee(BigDecimal amount, Double rate, int term, String typeTerm, int paymentPeriod){

        Double termd = new Double(term);
        int typeTermNumber = getTypeTermNumber(typeTerm);
        termd = termd*typeTermNumber;
        int feeNumber = (int) (termd/paymentPeriod);

        return amount.divide(new BigDecimal(feeNumber), RoundingMode.UP).setScale(2,RoundingMode.UP);
    }



    public BigDecimal getInterestVariableFee(BigDecimal amount, Double rate, int feeNumber ){

        return amount.multiply(new BigDecimal(rate/100)).divide(new BigDecimal(feeNumber),BigDecimal.ROUND_UP).setScale(2,RoundingMode.UP);
    }

    public BigDecimal getInterestVariableFeeTermFixed(BigDecimal amount, Double rate, int feeNumber, int term ){
        Double amt = amount.doubleValue();
        Double aux = amt * (rate / 100) / (Double.valueOf(feeNumber)/360.0 );
        BigDecimal dailyInterest = amount.multiply(new BigDecimal(rate/100))
                .divide(new BigDecimal(feeNumber),MathContext.DECIMAL128).setScale(2,RoundingMode.UP).divide(new BigDecimal(360),MathContext.DECIMAL128);

//        BigDecimal dailyInterest = new BigDecimal(aux);

        return dailyInterest.multiply(new BigDecimal(term)).setScale(2, RoundingMode.UP);
    }

    public int getTypeTermNumber(String typeTerm) {
        if (typeTerm.equals("DIA")) return  1;
        if (typeTerm.equals("MES")) return  30;
        if (typeTerm.equals("ANUAL")) return  360;
        return 0;
    }

    public BigDecimal getInterestFixedFee(BigDecimal amount, Double rate, long days, int paymentPeriod){
        BigDecimal a = BigDecimal.valueOf(days).divide(new BigDecimal(360),10,BigDecimal.ROUND_UP).setScale(7,RoundingMode.UP);
        return (amount.multiply(a).multiply(new BigDecimal(rate/100))).setScale(2,RoundingMode.UP);
    }


    public static double[] getListFee(List<PaymentPlan> paymentPlanList,Double amount){
      double[] listFee = new double[paymentPlanList.size()];
      int i = 0;
        for(PaymentPlan p:paymentPlanList){
            if (i==0) {
                listFee[i]=amount*-1;
            }else{
                listFee[i]=p.getFee();
            }
            i++;
        }
        return listFee;
    }

    public static Date[] datePayments(List<PaymentPlan> paymentPlanList){
        Date[] listDate = new Date[paymentPlanList.size()];
        int i =0;
        for(PaymentPlan p:paymentPlanList){
            listDate[i] = Date.from(p.getPaymentDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            i++;
        }
        return listDate;
    }

    public static double irr(double[] values, double guess) {
        final int maxIterationCount = 20;
        final double absoluteAccuracy = 1E-7;

        double x0 = guess;
        double x1;

        int i = 0;
        while (i < maxIterationCount) {

            // the value of the function (NPV) and its derivate can be calculated in the same loop
            final double factor = 1.0 + x0;
            int k = 0;
            double fValue = values[k];
            double fDerivative = 0;
            for (double denominator = factor; ++k < values.length; ) {
                final double value = values[k];
                fValue += value / denominator;
                denominator *= factor;
                fDerivative -= k * value / denominator;
            }

            // the essense of the Newton-Raphson Method
            x1 = x0 - fValue/fDerivative;

            if (Math.abs(x1 - x0) <= absoluteAccuracy) {

                return x1;
            }

            x0 = x1;
            ++i;
        }
        // maximum number of iterations is exceeded
        return Double.NaN;
    }

    public static double irr2(double[] fees, double init){
        int k=0;
        int p=1;
        double denominator = 0.0;
        double result=0.0;
        for(double j=0.00001;++k<fees.length;){

            denominator = Math.pow(1+j,k);
            result = (fees[k]/denominator)+result;

        }

        return 0.0;
    }


    public static double correctPaymentPlan(List<PaymentPlan> paymentPlanList, Double fee){

        int size = paymentPlanList.size()-1;
        PaymentPlan last = paymentPlanList.get(size);
        PaymentPlan beforeLast = paymentPlanList.get(size-1);
        Double lastFee = last.getCapital() + last.getInterest();
        Double residueBeforeLastFee = beforeLast.getResidue();
        if(lastFee>fee){
            return 0.01;
        }else if(residueBeforeLastFee < 0) {
            return -0.01;
        }else return 0.0;

    }

    public static double irr3(double[] values, double guess) {
         final int MAX_ITERATION_COUNT = 20;
         final double ABSOLUTE_ACCURACY = 1E-7;
        double x0 = guess;

        for (int i = 0; i < MAX_ITERATION_COUNT; i++) {

            // the value of the function (NPV) and its derivate can be calculated in the same loop
            final double factor = 1.0 + x0;
            double denominator = factor;
            if (denominator == 0) {
                return Double.NaN;
            }

            double fValue = values[0];
            double fDerivative = 0;
            for (int k = 1; k < values.length; k++) {
                final double value = values[k];
                fValue += value / denominator;
                denominator *= factor;
                fDerivative -= k * value / denominator;
            }

            // the essence of the Newton-Raphson Method
            if (fDerivative == 0) {
                return Double.NaN;
            }
            double x1 =  x0 - fValue/fDerivative;

            if (Math.abs(x1 - x0) <= ABSOLUTE_ACCURACY) {
                return x1;
            }

            x0 = x1;
        }
        // maximum number of iterations is exceeded
        return Double.NaN;
    }

    public static double getItf(Double fee, double rateItf, String currency){
        BigDecimal itfd = BigDecimal.ZERO;
        if(currency.equals("$us.")){
            itfd=BigDecimal.valueOf(fee).multiply(BigDecimal.valueOf(rateItf/1000)).setScale(2,RoundingMode.UP);
        }

        return itfd.doubleValue();
    }
}
