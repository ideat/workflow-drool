package com.mindware.workflow.core.service.data.creditResolution.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class CreditResolutionCreditRequestDto {
    private UUID idCreditRequestApplicant;
    private Integer numberRequest;
    private String typeCredit;
    private String firstName;
    private String secondName;
    private String lastName;
    private String motherLastName;
    private String state;
    private Double amount;
    private String currency;
    private String loginUser;
    private Integer numberApplicant;
    private String city;
    private String nameUser;

    public String getFullName(){
        return  Optional.ofNullable(this.firstName).orElse("") + " "
                +Optional.ofNullable(this.secondName).orElse("")+ " "
                +Optional.ofNullable(this.lastName).orElse("")+" "
                +Optional.ofNullable(this.motherLastName).orElse("");

    }
}
