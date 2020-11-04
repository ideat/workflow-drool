package com.mindware.workflow.core.service.data.legal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class LegalInformationCreditRequestDto {
    private Integer numberRequest;

    private Integer numberApplicant;

    private String firstName;

    private String lastName;

    private Double amount;

    private String currency;

    private String state;

    private String typeGuarantee;

    private String city;

    private String official;

    private boolean hasGuarantee;

    private boolean hasPatrimonialStatement;

    private boolean hasGuarantor;

    public String getFullName(){
        return Optional.ofNullable(this.lastName).orElse("")+" "
                +Optional.ofNullable(this.firstName).orElse("");
    }
}
