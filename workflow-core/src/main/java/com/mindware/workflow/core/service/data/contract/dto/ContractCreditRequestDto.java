package com.mindware.workflow.core.service.data.contract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractCreditRequestDto {
    private String firstName;
    private String lastName;
    private Integer numberApplicant;
    private Integer numberRequest;
    private String nameOfficer;
    private String city;
    private String nameOffice;
    private String pathContract;
}
