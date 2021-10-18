package com.mindware.workflow.core.service.data.cashFlow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CashFlowCreditRequestReportDto {
    private String fullName;

    private List<CashFlowPeriod> flowItemsPeriod;

    private Double amount;

    private String currency;

    private String currencyName;

    private LocalDate requestDate;

    private String description;

    private Integer numberRequest;
}
