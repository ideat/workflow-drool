package com.mindware.workflow.core.service.data.office.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignatoriesDto {
    private UUID id;
    private String nameSignatorie;
    private String identifyCardSignatorie;
    private String position;
    private String status;
    private String notaryPower;
    private String datePower;
    private String numberNotary;
    private String notaryName;
    private String judicialDistrict;
    private String testimonyNumber;
    private String testimonyDate;
    private String priority;

}
