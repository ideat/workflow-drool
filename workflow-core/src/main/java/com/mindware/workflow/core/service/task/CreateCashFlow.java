package com.mindware.workflow.core.service.task;

import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.entity.cashFlow.FlowItem;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateCashFlow {
    private  List<FlowItem> cashflow = new ArrayList<>();

    private int size;
    private Integer cont;
    private int frecuency;

    public  List<FlowItem> createCashFlowMonth(List<PaymentPlan> paymentPlanList
            , List<PatrimonialStatement> patrimonialStatementList, CreditRequest creditRequest){
        cont = 0;
        frecuency = creditRequest.getPaymentPeriod()/30;

        if(frecuency==1){
            size = creditRequest.getTerm() + frecuency+1;
        }else if(frecuency%2==0 ) {
            size = creditRequest.getTerm() + frecuency + 1; //TODO: CHANGE..ADDED + 1, REVIEW
        }else {
            size = creditRequest.getTerm() + frecuency - 1;
        }

        String[] headers = new String[size];
        int i=1;
        int aux= 0;
        for(PaymentPlan p : paymentPlanList){
            if(frecuency==1) {
                headers[i] = p.getPaymentDate().format(DateTimeFormatter.ofPattern("MM-yyyy"));
                i++;
            }else{
                if(aux==0){
                    headers[i] = p.getPaymentDate().plusMonths(-1).format(DateTimeFormatter.ofPattern("MM-yyyy"));
                    i++;
                }
                if(aux==1) {

                    for (int j = frecuency ; j > 0; j--) {
                        headers[i] = p.getPaymentDate().plusMonths(j *-1).format(DateTimeFormatter.ofPattern("MM-yyyy"));
                        i++;
                    }

                }else if(aux>1){
                    for (int j = frecuency-1 ; j >= 0; j--) {
                        headers[i] = p.getPaymentDate().plusMonths(j *-1).format(DateTimeFormatter.ofPattern("MM-yyyy"));
                        i++;
                    }
                }
                aux++;

            }
        }
//        if(frecuency!=1) {
//            headers[i] = paymentPlanList.get(aux-1 ).getPaymentDate().format(DateTimeFormatter.ofPattern("MM-yyyy"));
//            i++;
//        }

        headers[0] = "DETALLE";
        cashflow.add(createFlowItem(headers,cont,"DETAIL"));

        cont++;
        String[] subHeader1 = new String[size];
//        subHeader1[0] = cont.toString();
        subHeader1[0] = "INGRESOS(+)";
        subHeader1 = fillSubHeaders(subHeader1);
        cashflow.add(createFlowItem(subHeader1,cont,"HEADER_EARNING"));
        cont++;

        List<PatrimonialStatement> income = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("INGRESOS")).collect(Collectors.toList());
        List<FlowItem> tempIncome = new ArrayList<>();
        tempIncome = fillListArray(income,"EARNING");
        cashflow.addAll(tempIncome);

        cont++;
        String[] sumIncome = sumItems(getItems(tempIncome));
//        sumIncome[0] = cont.toString();
        sumIncome[0] = "TOTAL INGRESOS";
        Integer contSumIncome = cont;
        cashflow.add(createFlowItem(sumIncome,cont,"TOTAL_EARNING"));
        cont++;

        String[] subHeader2 = new String[size];
//        subHeader2[0] = cont.toString();
        subHeader2[0] = "EGRESOS(-)";
        subHeader2 = fillSubHeaders(subHeader2);
        cashflow.add(createFlowItem(subHeader2,cont,"HEADER_EXPENSE"));
        cont++;

        List<PatrimonialStatement> expenses = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("EGRESOS")).collect(Collectors.toList());

        List<FlowItem> tempExpenses = new ArrayList<>();
        tempExpenses = fillListArray(expenses,"EXPENSE");

        tempExpenses.addAll(fillPaymentPlan(paymentPlanList));
        cashflow.addAll(tempExpenses);
        cont++;
        String[] sumExpenses = new String[size];
        sumExpenses = sumItems(getItems(tempExpenses));
        sumExpenses[0] = "TOTAL EGRESOS";
//        sumExpenses[0] = cont.toString();
        cashflow.add(createFlowItem(sumExpenses,cont,"TOTAL_EXPENSE"));

        cont++;
        List<String[]> valuesIncomeSuperAvit = calculateSuperAvit(sumIncome,sumExpenses);
//        cashflow.removeIf(v -> v.getOrder().equals(contSumIncome));
        cashflow.add(createFlowItem(valuesIncomeSuperAvit.get(0),cont,"INCOME_SUPER_AVIT"));
        cashflow.add(createFlowItem(valuesIncomeSuperAvit.get(1),cont++,"EXPENSE_SUPER_AVIT"));

        cashflow = formatNumber();
        return cashflow;

    }

    private List<FlowItem> formatNumber(){
        List<FlowItem> flowItemList = new ArrayList<>();

        for(FlowItem f:cashflow){
            if(!f.getOrder().equals(0)) {
                String[] item = f.getItem();
                for (int i = 1; i < size; i++) {
                    if(!item[i].equals(""))
                        item[i] = String.format("%,.2f", Double.parseDouble(item[i]));
                }
                f.setItem(item);
                flowItemList.add(f);
            }else{
               flowItemList.add(f);
            }
        }
        return flowItemList;
    }

    private List<String[]> calculateSuperAvit(String[] income, String[] expenses){
        String[] diff = new String[size];
        String[] acumulate = new String[size];
        List<String[]> result = new ArrayList<>();
        for(int i=1;i<size;i++){
            if(i==1) {
                Double dif = Double.parseDouble(income[i]) - Double.parseDouble(expenses[i]); // previous data
                diff[i] = dif.toString();
                acumulate[i] = diff[i];
            }else{
                Double dif = Double.parseDouble(income[i]) - Double.parseDouble(expenses[i]);
                diff[i] = dif.toString();
                Double acum = Double.parseDouble(acumulate[i-1]) + dif;
                acumulate[i] =  acum.toString();
            }
        }
        diff[0] = "SUPER AVIT";
        acumulate[0] = "ACUMULADOS";
        result.add(diff);
        result.add(acumulate);
        return result;
    }

    private List<FlowItem> fillListArray(List<PatrimonialStatement> patrimonialStatementList,String type){
        List<FlowItem> items = new ArrayList<>();
        for(PatrimonialStatement p : patrimonialStatementList){
            String[] item = new String[size];
            item[0] = p.getFieldText1();
//            item[0] = cont.toString();

            cont++;
            item = fillArrayWithData(item,p.getFieldDouble1());

            items.add(createFlowItem(item,cont,type));
        }

        return items;
    }

    private List<FlowItem> fillPaymentPlan(List<PaymentPlan> paymentPlanList){
        List<String[]> items = new ArrayList<>();
        List<FlowItem> flowItems = new ArrayList<>();
        String[] capital = new String[size];
        String[] interest = new String[size];
        capital[0] = "CAPITAL";
        interest[0] = "INTERESES";
//        capital[0] = cont.toString();
        cont++;
//        interest[0] = cont.toString();
        int i = 1;
        for(PaymentPlan p:paymentPlanList){
            if(frecuency==1) {
                capital[i] = p.getCapital().toString();// String.format("%.,2f",p.getCapital());
                Double charges = p.getInterest() + p.getSecureCharge() + p.getOtherCharge() + p.getItf();
                interest[i] = charges.toString();
                i++;
            }else{
                if(i==1) {
                    for (int j = 0; j < frecuency-1; j++) {
                        capital[i] = "0.0";
                        interest[i] = "0.0";
                        i++;
                    }
                    capital[i] = p.getCapital().toString();// String.format("%.,2f",p.getCapital());
                    Double charges = p.getInterest() + p.getSecureCharge() + p.getOtherCharge() + p.getItf();
                    interest[i] = charges.toString();
                    i++;
                }else{
                    capital[i] = p.getCapital().toString();// String.format("%.,2f",p.getCapital());
                    Double charges = p.getInterest() + p.getSecureCharge() + p.getOtherCharge()+ p.getItf();
                    interest[i] = charges.toString();
                    i++;
                    if(i<size-1)
                    for (int j = 1; j < frecuency; j++) {
                        capital[i] = "0.0";
                        interest[i] = "0.0";
                        i++;
                    }

                }

            }

        }
        items.add(capital);
        items.add(interest);
        flowItems.add(createFlowItem(capital,cont,"EXPENSE"));
        flowItems.add(createFlowItem(interest,cont,"EXPENSE"));

        return flowItems;
    }


    private String[] sumItems(List<String[]> items){
        String[] result = new String[size];
        if(items.size()>=1) {
            for(int i=1;i<size;i++){
                List<Double> values = new ArrayList<>();
                for(int j=0;j<items.size();j++){
                    String[] temp = items.get(j);
                    try {
                        values.add(Double.parseDouble(temp[i]));
                    }catch (Exception e){
                        System.out.print(e);
                    }

                }
                Double sum = values.stream().mapToDouble(f -> f.doubleValue()).sum();
                result[i] = sum.toString(); //String.format("%,.2f",sum);
            }
        }

        return result;
    }


    private String[] fillArrayWithData(String[] origin, Double data ){

        for(int i=1;i<size;i++){
            origin[i] = data.toString();// String.format("%.2f",data);
        }

        return origin;
    }

    private String[] fillSubHeaders(String[] header){
        for(int i=1;i<size;i++){
            header[i]="";
        }
        return header;
    }

    private FlowItem createFlowItem(String[] item, Integer cont, String type){
        FlowItem flowItem = new FlowItem();
        flowItem.setOrder(cont);
        flowItem.setItem(item);
        flowItem.setType(type);
        return flowItem;
    }

    private List<String[]> getItems(List<FlowItem> flowItemList){
        List<String[]> strings = new ArrayList<>();
        for(FlowItem f:flowItemList){
            strings.add(f.getItem());
        }
        return strings;
    }

    private int getSize(CreditRequest creditRequest, Integer sizePaymentPlan){
        Integer count;
//        if(sizePaymentPlan%2==0){
            Integer paymentPeriod = creditRequest.getPaymentPeriod()/30;
            count = sizePaymentPlan*(paymentPeriod+1)-(paymentPeriod+1);
//        }

        return count;
    }
}
