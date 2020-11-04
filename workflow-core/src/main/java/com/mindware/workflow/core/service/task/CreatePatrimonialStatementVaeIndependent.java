package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.patrimonialStatement.SalesHistory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.PatrimonialStatementSalesHistoryDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.SummarySalesDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementVaeIndependent {

    Integer frecuency;
    Double  totalBuys, mbt, mubp, totalSales;
    List<Double> listTotals = new ArrayList<>();
    List<Double> listTotalsBuys = new ArrayList<>();
    PatrimonialStatementSalesHistoryDto patrimonialStatementSalesHistoryDto;
    List<OperativeExpenses> listOperativeExpenses;

    public PatrimonialStatementVaeIndependentDto generateVaeIndependent(Applicant applicant
            , PatrimonialStatement patrimonialStatement) throws IOException {

        frecuency = patrimonialStatement.getFieldInteger1()==null?0:patrimonialStatement.getFieldInteger1();
        CreatePatrimonialStatementSales createPatrimonialStatementSales = new CreatePatrimonialStatementSales();
        String jsonSalesHistory = patrimonialStatement.getFieldText2()==null?"[]":patrimonialStatement.getFieldText2() ;
        ObjectMapper mapper = new ObjectMapper();
        List<SalesHistory> salesHistory = mapper.readValue(jsonSalesHistory,new TypeReference<List<SalesHistory>>(){});
        patrimonialStatementSalesHistoryDto = createPatrimonialStatementSales.generateSales(applicant,salesHistory);


        PatrimonialStatementVaeIndependentDto pvae = new PatrimonialStatementVaeIndependentDto();

        if(patrimonialStatement.getFieldText3()==null || patrimonialStatement.getFieldText3().equals("")) patrimonialStatement.setFieldText3("[]");
        if(patrimonialStatement.getFieldText4()==null || patrimonialStatement.getFieldText4().equals("")) patrimonialStatement.setFieldText4("[]");
        if(patrimonialStatement.getFieldText5()==null || patrimonialStatement.getFieldText5().equals("")) patrimonialStatement.setFieldText5("[]");

        List<SalesProjection> salesProjectionList = Arrays.asList(mapper.readValue(patrimonialStatement.getFieldText3(), SalesProjection[].class));
        List<ProductSalesBuys> productSalesBuysList = Arrays.asList(mapper.readValue(patrimonialStatement.getFieldText4(), ProductSalesBuys[].class));

        List<SalesProjection> listSaleDaily = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("DIARIA")
                && !p.getTypeSale().equals("")).collect(Collectors.toList());
        List<SalesProjection> listSaleWeek = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("SEMANAL")
                && !p.getTypeSale().equals("")).collect(Collectors.toList());
        List<SalesProjection> listSaleMonth = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("MENSUAL")
                && !p.getTypeSale().equals("")).collect(Collectors.toList());

        List<SalesProjection> listAmountDaily = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("DIARIA")
                && p.getTypeSale().equals("")).collect(Collectors.toList());
        List<SalesProjection> listAmountWeek = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("SEMANAL")
                && p.getTypeSale().equals("")).collect(Collectors.toList());
        List<SalesProjection> listAmountMonth = salesProjectionList.stream().filter(p -> p.getCategorySale().equals("MENSUAL")
                && p.getTypeSale().equals("")).collect(Collectors.toList());

        List<ProductSalesBuys> listProductSales = productSalesBuysList.stream().filter(p -> p.getTypeRegister().equals("VENTA"))
                .collect(Collectors.toList());
        List<ProductSalesBuys> listProductBuys = productSalesBuysList.stream().filter(p -> p.getTypeRegister().equals("COMPRA"))
                .collect(Collectors.toList());
        listOperativeExpenses = Arrays.asList(mapper.readValue(patrimonialStatement.getFieldText5(), OperativeExpenses[].class));

        List<SummaryAmount> summaryDay = getResumeProjection(listSaleDaily,listAmountDaily,"DIARIA");
        List<SummaryAmount> summaryWeek = getResumeProjection(listSaleWeek,listAmountWeek,"SEMANAL");
        List<SummaryAmount> summaryMonth = getResumeProjection(listSaleMonth,listAmountMonth,"MENSUAL");

        List<ProductSales> productSalesList = getPercentageTotalSales(listProductSales);
        Double totalBuysSales = listProductSales.stream()
                .map(b -> b.getTotalBuys())
                .reduce(0.0,Double::sum);
        Double totalBuysSales2 = ((totalBuysSales*mbt)/(1-mbt))+totalBuysSales;
        if(totalBuysSales2>0) listTotals.add(totalBuysSales2);

        List<ProductSalesBuys> productSalesBuys = getPercentageTotalBuys(listProductBuys);
        Double totalBuys = listProductBuys.stream()
                .map(b -> b.getTotalBuys())
                .reduce(0.0,Double::sum);
        if(totalBuys>0) listTotalsBuys.add(totalBuys);

        pvae.setFullName(applicant.getFullName());
        pvae.setActivity(patrimonialStatement.getFieldText1());
        pvae.setListSaleDaily(listSaleDaily);
        pvae.setListSaleWeek(listSaleWeek);
        pvae.setListSaleMonth(listSaleMonth);
        pvae.setListAmountDaily(listAmountDaily);
        pvae.setListAmountDaily(listAmountDaily);
        pvae.setListAmountWeek(listAmountWeek);
        pvae.setListAmountMonth(listAmountMonth);
        pvae.setListProductSales(productSalesList);
        pvae.setListProductBuys(productSalesBuys);
        pvae.setListOperativeExpenses(listOperativeExpenses);
        pvae.setListSummaryDay(summaryDay);
        pvae.setListSummaryWeek(summaryWeek);
        pvae.setListSummaryMonth(summaryMonth);
        pvae.setListEarningExpenses(getEarningExpensesSummary());

        return pvae;
    }

    private List<ProductSales> getPercentageTotalSales(List<ProductSalesBuys> productSalesBuysList){
        List<ProductSales> productSalesList = new ArrayList<>();
        totalSales = productSalesBuysList.stream()
                .map(s -> s.getTotalSales())
                .reduce(0.0,Double::sum);

        for(ProductSalesBuys p:productSalesBuysList){
            ProductSales productSales = new ProductSales();
            productSales.setProduct(p.getProduct());
            productSales.setUnit(p.getUnit());
            productSales.setQuantity(p.getQuantity());
            productSales.setFrecuency(p.getFrecuency());
            productSales.setPriceCost(p.getPriceCost());
            productSales.setPriceSale(p.getPriceSale());
            productSales.setTotalBuys(p.getTotalBuys());
            productSales.setTotalSales(p.getTotalSales());
            productSales.setPercentageTotalSales(p.getTotalSales()/ totalSales);
            productSales.setMub((p.getPriceSale()-p.getPriceCost())/p.getPriceSale());
            BigDecimal mubd = new BigDecimal(productSales.getMub()*productSales.getPercentageTotalSales());

            productSales.setMubp(mubd.setScale(4, RoundingMode.HALF_UP).doubleValue());
            productSalesList.add(productSales);
        }
        if(totalSales>0) listTotals.add(totalSales);

        totalBuys = productSalesList.stream()
                .map(b -> b.getTotalBuys())
                .reduce(0.0,Double::sum);
        mubp = productSalesList.stream()
                .map(b -> b.getMubp())
                .reduce(0.0,Double::sum);
        Double auxmbt =  Double.isFinite ((totalSales - totalBuys)/totalSales)==false?0:(totalSales - totalBuys)/totalSales;
        mbt=Math.round(auxmbt*10000.0) / 10000.0;

        if(totalBuys>0) listTotalsBuys.add(totalBuys);

        return productSalesList;
    }

    private List<ProductSalesBuys> getPercentageTotalBuys(List<ProductSalesBuys> productSalesBuysList){
        List<ProductSalesBuys> productBuysList = new ArrayList<>();
        Double totalBuys = productSalesBuysList.stream()
                .map(b -> b.getTotalBuys())
                .reduce(0.0,Double::sum);

        Double totalBuys2 = ((totalBuys*mubp)/(1 - mubp))+totalBuys;
//        listTotals.add(totalBuys2);

        totalBuys2 = Math.round(totalBuys2*100.0)/100.0;
        for(ProductSalesBuys p:productSalesBuysList){
            ProductSalesBuys productBuy = new ProductSalesBuys();
            productBuy.setProduct(p.getProduct());
            productBuy.setUnit(p.getUnit());
            productBuy.setQuantity(p.getQuantity());
            productBuy.setFrecuency(p.getFrecuency());
            productBuy.setPriceCost(p.getPriceCost());
            productBuy.setTotalBuys(p.getTotalBuys());
            productBuy.setTotalSales(totalBuys2); //total ventas segun compras
            productBuy.setPriceSale(1-mubp); //1 - mubt

            productBuysList.add(productBuy);
        }

        return productBuysList;
    }

    private List<SummaryAmount> getResumeProjection(List<SalesProjection> listFrecuency, List<SalesProjection> listAmounts, String period){
        List<SummaryAmount> result = new ArrayList<>();
        int countLow = 0;
        int countRegular = 0;
        int countHight = 0;

        for(SalesProjection s:listFrecuency){
            if(s.getTypeSale().equals("ALTA")) countHight+=1;
            if(s.getTypeSale().equals("REGULAR")) countRegular+=1;
            if(s.getTypeSale().equals("BAJA")) countLow+=1;
        }
        for(SalesProjection s : listAmounts){
            SummaryAmount a = new SummaryAmount();
            if(s.getPeriod().equals("ALTA")) {
                a.setName("ALTA");
                a.setAmount(s.getAverageSale()*  countHight);
            }else if(s.getPeriod().equals("REGULAR")) {
                a.setName("REGULAR");
                a.setAmount(s.getAverageSale()*  countRegular);
            }else if(s.getPeriod().equals("BAJA")) {
                a.setName("BAJA");
                a.setAmount(s.getAverageSale()*  countLow);
            }else{
                a.setName("");
                a.setAmount(0.0);
            }

            result.add(a);
        }

        SummaryAmount sumMonth = new SummaryAmount();
        Double sale = 0.0;
        for(SummaryAmount s :result){
            sale +=s.getAmount();
        }
        if(period.equals("DIARIA")) {
            SummaryAmount sumSale = new SummaryAmount();

            sale =  Double.isFinite(sale / (countHight + countRegular + countLow))==false?0:sale / (countHight + countRegular + countLow);
            sumSale.setAmount(sale);
            sumSale.setName("VENTA DIARIA");
            result.add(sumSale);
            sumMonth.setName("VENTAS MES");
            sumMonth.setAmount(sale*frecuency);
            if(sumMonth.getAmount()>0) {
                listTotals.add(sumMonth.getAmount());
            }
        }else if (period.equals("SEMANAL")){
            sumMonth.setAmount(sale);
            if(sumMonth.getAmount()>0) {
                listTotals.add(sumMonth.getAmount());
            }
        }else if (period.equals("MENSUAL")){
            sumMonth.setAmount(sale/12);
            if(sumMonth.getAmount()>0) {
                listTotals.add(sumMonth.getAmount());
            }
        }
//        SummarySalesDto summarySalesDto = patrimonialStatementSalesHistoryDto.getListSummary().get(7);
////        listTotalsBuys.add(summarySalesDto.getTotal());//incorrect
////
////
//        listTotals.add(summarySalesDto.getTotal());

        sumMonth.setName("VENTAS MES");
        result.add(sumMonth);

        return result;
    }

    private List<SummaryAmount> getEarningExpensesSummary(){
        List<SummaryAmount> listEarningExpenses = new ArrayList<>();
        Double minValue = listTotals.stream().min(Comparator.naturalOrder()).get();
        listTotalsBuys.add(minValue*(1-mubp));
        Double maxValue = listTotalsBuys.stream().max(Comparator.naturalOrder()).get();

        List<SummarySalesDto> summarySalesDtoList = patrimonialStatementSalesHistoryDto.getListSummary();
        SummarySalesDto summarySalesDto = new SummarySalesDto();
        if(summarySalesDtoList==null || summarySalesDtoList.equals("[]")) {

            summarySalesDto.setTotal(0.0);
        }else {
            summarySalesDto = summarySalesDtoList.get(7);
        }
            listEarningExpenses.add(newSummaryAmount("Total ingreso x ventas", minValue));
            listEarningExpenses.add(newSummaryAmount("Margen Bruto Ponderado", mubp * 100.0));
            listEarningExpenses.add(newSummaryAmount("Total costo x ventas", maxValue));
            listEarningExpenses.add(newSummaryAmount("Utilidad Bruta", minValue - maxValue));
            listEarningExpenses.add(newSummaryAmount("Ventas segun tabulacion Promedio", summarySalesDto.getTotal()));
            Double auxAmount = (1 - mubp) * summarySalesDto.getTotal();
            auxAmount = Math.round(auxAmount * 100.0) / 100.0;
            listEarningExpenses.add(newSummaryAmount("Costos segun el MUB determinado", auxAmount));
            listEarningExpenses.add(newSummaryAmount("Utilidad Bruta segun MUB", summarySalesDto.getTotal() - auxAmount));
            Double totalOperativeExpenses = listOperativeExpenses.stream()
                    .map(b -> b.getAmount())
                    .reduce(0.0, Double::sum);
            listEarningExpenses.add(newSummaryAmount("Utilidad Operativa segun MUB", summarySalesDto.getTotal() - auxAmount - totalOperativeExpenses));
            Double operativeEarning = minValue - maxValue - totalOperativeExpenses;
            operativeEarning = Math.round(operativeEarning * 100.0) / 100.0;
            listEarningExpenses.add(newSummaryAmount("Utilidad Operativa", operativeEarning));
            return listEarningExpenses;

    }

    private SummaryAmount newSummaryAmount(String name, Double amount){
        SummaryAmount s = new SummaryAmount();
        s.setName(name);
        s.setAmount(amount);
        return s;
    }



}
