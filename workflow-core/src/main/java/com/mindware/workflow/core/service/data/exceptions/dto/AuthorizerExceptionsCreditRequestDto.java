package com.mindware.workflow.core.service.data.exceptions.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class AuthorizerExceptionsCreditRequestDto {
    private Integer numberRequest;
    private Integer numberApplicant;
    private Double amount;
    private String currency;
    private String firstName;
    private String lastName;
    private String stateCreditRequest;
    private Integer totalExceptionsProposal;
    private Integer totalExceptionsRejected;
    private Integer totalExceptionsApproved;
    private String city;
    private String loginUser;
}
