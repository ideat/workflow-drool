package com.mindware.workflow.core.entity.kiosco;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SummaryCreditRequest {
    private Integer numberRequest;

    private Double amount;

    private String currency;

    private LocalDate requestDate;

    private String summary;

    private Double rateInterest;

    private Integer term;

    private String typeTerm;

    private Integer paymentPeriod;

    private String typeFee;

    private String officerName;
}
