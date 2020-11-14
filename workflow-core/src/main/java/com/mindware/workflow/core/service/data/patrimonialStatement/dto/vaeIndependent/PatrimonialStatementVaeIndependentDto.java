package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatrimonialStatementVaeIndependentDto {
    private String fullName;

    private String activity;

    private Double typeChangeDollar;

    private Integer frecuency;

    private List<SalesProjection> listSaleDaily;

    private List<SalesProjection> listSaleWeek;

    private List<SalesProjection> listSaleMonth;

    private List<SalesProjection> listAmountDaily;

    private List<SalesProjection> listAmountWeek;

    private List<SalesProjection> listAmountMonth;

    private List<ProductSales> listProductSales;

    private List<ProductSalesBuys> listProductBuys;

    private List<OperativeExpenses> listOperativeExpenses;

    private List<SummaryAmount> listSummaryDay;

    private List<SummaryAmount> listSummaryWeek;

    private List<SummaryAmount> listSummaryMonth;

    private List<SummaryAmount> listEarningExpenses;



}
