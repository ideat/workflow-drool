package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatrimonialStatementSalesHistoryDto {

//    private UUID idCreditRequestApplicant;

    private String fullName;

    private String activity;

    private LocalDate date;

    private Double typeChange;

    private List<SalesMonthDto> listMonth1;

    private List<SalesMonthDto> listMonth2;

    private List<SalesMonthDto> listMonth3;

    private List<SalesMonthDto> listMonth4;

    private List<SalesMonthDto> listMonth5;

    private List<SalesMonthDto> listMonth6;

    private List<SummarySalesDto> listSummary;



}
