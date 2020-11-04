package com.mindware.workflow.core.service.data.exceptions.dto;

import lombok.Data;

@Data
public class ExceptionsApplicantCreditRequestDto {
    private Integer numberRequest;
    private String namesApplicant;
    private String lastNamesApplicant;
    private Double amount;
    private String currency;
    private String city;
    private String typeCredit;
    private String typeGuarantee;
    private String codeException;
    private String description;
    private String justification;
    private Integer daysElapsed;
    private Integer limitTime;
    private Integer remainingDays;
    private String loginUser;
    private String email;
    private String riskType;

    public String getFullName(){
        return namesApplicant+" "+ lastNamesApplicant;
    }
}
