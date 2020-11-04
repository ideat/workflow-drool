package com.mindware.workflow.core.service.task;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.patrimonialStatement.SalesHistory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.PatrimonialStatementSalesHistoryDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.SalesMonthDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.SummarySalesDto;

import java.util.*;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementSales {
    List<SalesHistory> salesHistoryList;
    List<SummarySalesDto> summarySalesDtoList;

    public PatrimonialStatementSalesHistoryDto generateSales(Applicant applicant,
                                                             List<SalesHistory> salesHistories){
        salesHistoryList = salesHistories;
        List<PatrimonialStatementSalesHistoryDto> list = new ArrayList<>();
        summarySalesDtoList = new ArrayList<>();

        PatrimonialStatementSalesHistoryDto ps = new PatrimonialStatementSalesHistoryDto();
        if(salesHistories.equals("[]") || salesHistories.size()==0){

        }else {
            ps.setActivity(salesHistories.get(0).getConcept());
//        ps.setIdCreditRequestApplicant( salesHistories.get(0).getIdCreditRequestApplicant());
            ps.setDate(salesHistories.get(0).getDaySale());
            ps.setTypeChange(6.96); //TODO CHANGE BY OFFICIAL TYPE CHANGE
            ps.setFullName(applicant.getFullName());
            ps.setListMonth1(getSalesMonthDtos("MES-1"));
            ps.setListMonth2(getSalesMonthDtos("MES-2"));
            ps.setListMonth3(getSalesMonthDtos("MES-3"));
            ps.setListMonth4(getSalesMonthDtos("MES-4"));
            ps.setListMonth5(getSalesMonthDtos("MES-5"));
            ps.setListMonth6(getSalesMonthDtos("MES-6"));
            setResume();

            ps.setListSummary(summarySalesDtoList);
        }
        return ps;
    }

    private List<SalesMonthDto> getSalesMonthDtos(String element){
        Double total = 0.0;
        List<SalesHistory> list = salesHistoryList.stream()
                .filter(p -> p.getNumberMonth().equals(element))
                .collect(Collectors.toList());
        List<SalesMonthDto> salesMonthDtoList = new ArrayList<>();

        for(SalesHistory p : list){
            SalesMonthDto salesMonthDto = new SalesMonthDto();
            salesMonthDto.setActivity(p.getConcept());
            salesMonthDto.setAmount(p.getAmount());
            salesMonthDto.setDate(p.getDaySale());
            salesMonthDtoList.add(salesMonthDto);
            total = total + (p.getAmount()==null?0.0:p.getAmount());
        }
        salesMonthDtoList.sort(Comparator.comparing(a -> a.getDate()));

        SummarySalesDto s = new SummarySalesDto();
        s.setMonth(list.get(0).getDaySale().getMonth().name().toUpperCase());
        s.setTotal(total);
        String[] se = element.split("-");
        s.setSequence(Integer.parseInt(se[1]));
        summarySalesDtoList.add(s);

        return salesMonthDtoList;
    }

    private void setResume(){
        Double total = summarySalesDtoList.stream().map(s -> s.getTotal()).reduce(0.0,Double::sum);
        List<SummarySalesDto> count = summarySalesDtoList.stream()
                .filter(t -> t.getTotal()>0.0)
                .collect(Collectors.toList());
        Double average = total / count.size();
        SummarySalesDto totalSalesDto = new SummarySalesDto();
        totalSalesDto.setSequence(summarySalesDtoList.size()+1);
        totalSalesDto.setMonth("TOTAL");
        totalSalesDto.setTotal(total);
        summarySalesDtoList.add(totalSalesDto);

        SummarySalesDto averageSalesDto = new SummarySalesDto();
        averageSalesDto.setSequence(summarySalesDtoList.size()+1);
        averageSalesDto.setMonth("INGRESO PROMEDIO");
        averageSalesDto.setTotal(average);
        summarySalesDtoList.add(averageSalesDto);
    }


}
