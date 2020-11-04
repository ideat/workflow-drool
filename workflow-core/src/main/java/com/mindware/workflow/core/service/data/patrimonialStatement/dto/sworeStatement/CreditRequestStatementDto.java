package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreditRequestStatementDto {
    private Integer numberRequest;

    private String currency;

    private String currencyName;

    private Double amount;

    private String literalAmount;

    private String typeCredit;

    private String nameTypeCredit;

    private Integer term;

    private String typeTerm;

    private String paymentPeriod;

    private String typeFee;

    private Double rateInterest;

    private LocalDate requestDate;
}
