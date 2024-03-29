package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.entity.cashFlow.FlowItem;
import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestReportDto;
import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowPeriod;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CreateCashFlowInputReport {
    private CashFlowCreditRequestReportDto cashFlowCreditRequestReportDto;

    public CashFlowCreditRequestReportDto createInputReport(CashFlow cashFlow, CreditRequestApplicantdto creditRequestApplicantdto) throws IOException {
        cashFlowCreditRequestReportDto = new CashFlowCreditRequestReportDto();

        cashFlowCreditRequestReportDto.setAmount(creditRequestApplicantdto.getAmount());
        cashFlowCreditRequestReportDto.setCurrency(creditRequestApplicantdto.getCurrency());
        cashFlowCreditRequestReportDto.setFullName(creditRequestApplicantdto.getFullName());
        cashFlowCreditRequestReportDto.setRequestDate(creditRequestApplicantdto.getRequestDate());
        cashFlowCreditRequestReportDto.setDescription(cashFlow.getDescription());
        cashFlowCreditRequestReportDto.setNumberRequest(creditRequestApplicantdto.getNumberRequest());



        List<CashFlowPeriod> cashFlowPeriodList = new ArrayList<>();
        List<FlowItem> cashFlowItemList = getCashFlowItems(cashFlow.getItems());
        cashFlowPeriodList = generateCashFlowPeriod(cashFlowItemList);
        cashFlowCreditRequestReportDto.setFlowItemsPeriod(cashFlowPeriodList);

        return cashFlowCreditRequestReportDto;

    }

    private List<FlowItem> getCashFlowItems(String cashFlowItems) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<FlowItem> cashFlowItemList = mapper.readValue(cashFlowItems, new TypeReference<List<FlowItem>>(){});
        cashFlowItemList.stream().sorted(Comparator.comparing(FlowItem::getOrder));
        return cashFlowItemList;
    }

    private List<CashFlowPeriod> generateCashFlowPeriod(List<FlowItem> flowItemList){
        List<CashFlowPeriod> cashFlowPeriodList = new ArrayList<>();
        int cont=0;
        for(FlowItem flowItem:flowItemList) {
            String[] items = flowItem.getItem();
            if(cont==0){
                cashFlowPeriodList.add(headers(items));
                cont++;
            }else{
                if(items[0].equals("INGRESOS(+)")){
                    CashFlowPeriod cashFlowPeriod = new CashFlowPeriod();
                    cashFlowPeriod.setDetail(items[0]);
                    cashFlowPeriodList.add(cashFlowPeriod);
                }else  if(items[0].equals("EGRESOS(-)")){
                    CashFlowPeriod cashFlowPeriod = new CashFlowPeriod();
                    cashFlowPeriod.setDetail(items[0]);
                    cashFlowPeriodList.add(cashFlowPeriod);
                }else {

                    cashFlowPeriodList.add(itemsCashFlow(items));
                }
            }

        }
        return cashFlowPeriodList;
    }

    private String sumItem(int initPeriod, int numPeriods, String[] items){
        Double sum=0.0;
        if(initPeriod > items.length-1){
            initPeriod = items.length-1;
        }
        if(numPeriods > items.length){
            numPeriods = items.length-1;
        }
        for (int i = initPeriod; i <= numPeriods; i++) {
            if (i <= items.length) {
                if(items[i]!=null) {
                    sum = sum + Double.parseDouble(items[i].replace(".", "").replace(",", "."));
                }else{

                }
            }
        }

        return String.format("%,.2f",sum);
    }

    private  CashFlowPeriod headers(String[] items){
        CashFlowPeriod cashFlowPeriod = new CashFlowPeriod();
        int length = items.length - 1;
        
        cashFlowPeriod.setDetail(0<=length?items[0]:"");
        cashFlowPeriod.setPeriod0(1<=length?items[1]:"");
        cashFlowPeriod.setPeriod1(2<=length?items[2]:"");
        cashFlowPeriod.setPeriod2(3<=length?items[3]:"");
        cashFlowPeriod.setPeriod3(4<=length?items[4]:"");
        cashFlowPeriod.setPeriod4(5<=length?items[5]:"");
        cashFlowPeriod.setPeriod5(6<=length?items[6]:"");
        cashFlowPeriod.setPeriod6(7<=length?items[7]:"");
        cashFlowPeriod.setPeriod7(8<=length?items[8]:"");
        cashFlowPeriod.setPeriod8(9<=length?items[9]:"");
        cashFlowPeriod.setPeriod9(10<=length?items[10]:"");
        cashFlowPeriod.setPeriod10(11<=length?items[11]:"");
        cashFlowPeriod.setPeriod11(12<=length?items[12]:"");
        cashFlowPeriod.setPeriod12(13<=length?items[13]:"");
        cashFlowPeriod.setPeriod13((14<=length && 25<=length) ||(14<=length && length<=25)?"2do año":"");
//        cashFlowPeriod.setPeriod13((14<=length && 25<=length)?"2do año":"");
        cashFlowPeriod.setPeriod14(26<=length && 37<= length?"3er año":"");
        cashFlowPeriod.setPeriod15(38<=length && 49<= length?"4to año":"");
        cashFlowPeriod.setPeriod16(50<=length && 61<= length?"5to año":"");
        cashFlowPeriod.setPeriod17(62<=length && 73<= length?"6to año":"");
        cashFlowPeriod.setPeriod18(74<=length && 85<= length?"7mo año":"");
        cashFlowPeriod.setPeriod19(86<=length && 97<= length?"8vo año":"");
        cashFlowPeriod.setPeriod20(98<=length && 109<= length?"9no año":"");
        cashFlowPeriod.setPeriod21(110<=length && 121<= length?"10mo año":"");
        cashFlowPeriod.setPeriod22(122<=length && 181<= length?"11-15 años":"");
        cashFlowPeriod.setPeriod23(182<=length && 301<= length?"16-25 años":"");

        return cashFlowPeriod;
    }

    private CashFlowPeriod itemsCashFlow(String[] items){
        CashFlowPeriod cashFlowPeriod = new CashFlowPeriod();
        int length = items.length - 1;
        
        cashFlowPeriod.setDetail(0<=length?items[0]:"");
        cashFlowPeriod.setPeriod0(1<=length?items[1]:"");
        cashFlowPeriod.setPeriod1(2<=length?items[2]:"");
        cashFlowPeriod.setPeriod2(3<=length?items[3]:"");
        cashFlowPeriod.setPeriod3(4<=length?items[4]:"");
        cashFlowPeriod.setPeriod4(5<=length?items[5]:"");
        cashFlowPeriod.setPeriod5(6<=length?items[6]:"");
        cashFlowPeriod.setPeriod6(7<=length?items[7]:"");
        cashFlowPeriod.setPeriod7(8<=length?items[8]:"");
        cashFlowPeriod.setPeriod8(9<=length?items[9]:"");
        cashFlowPeriod.setPeriod9(10<=length?items[10]:"");
        cashFlowPeriod.setPeriod10(11<=length?items[11]:"");
        cashFlowPeriod.setPeriod11(12<=length?items[12]:"");
        cashFlowPeriod.setPeriod12(13<=length?items[13]:"");
        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod13((14 <= length && 25 <= length)  || (14 <= length && length <= 25)  ? sumItem(25, 25, items) : "");
        }else{
            cashFlowPeriod.setPeriod13((14 <= length && 25 <= length) || (14 <= length && length <= 25) ? sumItem(14, 25, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod14(26 <= length && 37 <= length ? sumItem(37, 37, items) : "");
        }else{
            cashFlowPeriod.setPeriod14(26<= length && 37 <= length ? sumItem(26, 37, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod15(38 <= length && 49 <= length ? sumItem(49, 49, items) : "");
        }else{
            cashFlowPeriod.setPeriod15(38 <= length && 49 <= length ? sumItem(38, 49, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod16(50 <= length && 61 <= length ? sumItem(61, 61, items) : "");
        }else{
            cashFlowPeriod.setPeriod16(50 <= length && 61 <= length ? sumItem(50, 61, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod17(62 <= length && 73 <= length ? sumItem(73, 73, items) : "");
        }else{
            cashFlowPeriod.setPeriod17(62 <= length && 73 <= length ? sumItem(62, 73, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod18(74 <= length && 85 <= length ? sumItem(85, 85, items) : "");
        }else{
            cashFlowPeriod.setPeriod18(74 <= length && 85 <= length ? sumItem(74, 85, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod19(86 <= length && 97 <= length ? sumItem(97, 97, items) : "");
        }else{
            cashFlowPeriod.setPeriod19(86 <= length && 97 <= length ? sumItem(86, 97, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod20(98 <= length && 109 <= length ? sumItem(109, 109, items) : "");
        }else{
            cashFlowPeriod.setPeriod20(98 <= length && 109 <= length ? sumItem(98, 109, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod21(110 <= length && 121 <= length ? sumItem(121, 121, items) : "");
        }else{
            cashFlowPeriod.setPeriod21(110 <= length && 121 <= length ? sumItem(110, 121, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod22(122 <= length && 181 <= length ? sumItem(181, 181, items) : "");
        }else{
            cashFlowPeriod.setPeriod22(122 <= length && 181 <= length ? sumItem(122, 181, items) : "");
        }

        if(items[0].equals("ACUMULADOS")) {
            cashFlowPeriod.setPeriod23(182 <= length && 301 <= length ? sumItem(301, 301, items) : "");
        }else{
            cashFlowPeriod.setPeriod23(182 <= length && 301 <= length ? sumItem(182, 301, items) : "");
        }

//        cashFlowPeriod.setPeriod14(26<=length && 37<=length?sumItem(26,37,items):"");
//        cashFlowPeriod.setPeriod15(38<=length && 49<=length?sumItem(38,49,items):"");
//        cashFlowPeriod.setPeriod16(50<=length && 61<=length?sumItem(50,61,items):"");
//        cashFlowPeriod.setPeriod17(62<=length && 73<=length?sumItem(62,73,items):"");
//        cashFlowPeriod.setPeriod18(74<=length && 85<=length?sumItem(74,85,items):"");
//        cashFlowPeriod.setPeriod19(86<=length && 97<=length?sumItem(86,97,items):"");
//        cashFlowPeriod.setPeriod20(98<=length && 109<=length?sumItem(98,109,items):"");
//        cashFlowPeriod.setPeriod21(110<=length && 121<=length?sumItem(110,121,items):"");
//        cashFlowPeriod.setPeriod22(122<=length && 181<=length ?sumItem(122,181,items):"");
//        cashFlowPeriod.setPeriod23(182<=length && 301<=length?sumItem(182,301,items):"");

        return cashFlowPeriod;
    }

}
