package com.mindware.workflow.core.service.data.creditScoring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditScoringCreditRequestDto {
    private Integer numberRequest;
    private String firstName;
    private String lastName;
    private String state;
    private Double amount;
    private String currency;
    private String city;
    private String official;
    private boolean hasPaymentPlan;
    private boolean hasPatrimonialStatement;
    private boolean hasCreditResolution;
    private String typeFee;
    private String typeGuarantee;
    private String typeCredit;
}
