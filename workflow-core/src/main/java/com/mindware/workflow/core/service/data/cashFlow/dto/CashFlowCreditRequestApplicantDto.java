package com.mindware.workflow.core.service.data.cashFlow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CashFlowCreditRequestApplicantDto {
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
    private UUID idCreditRequestApplicant;
    private String typeFee;
}
