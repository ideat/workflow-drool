package com.mindware.workflow.core.service.data.creditRequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreditRequestEnabledApplicantDto {
    private Integer numberRequest;
    private String currency;
    private Double amount;
    private LocalDate requestDate;
    private String firstName;
    private String lastName;
    private String cityOffice;
    private Integer codeOffice;
    private String nameOffice;
    private Instant disbursementDate; //fecha desembolso
    private Instant enabledDateTime;
    private Integer hoursEnabled;
    private Instant finishedDateTime;
    private String enablingUser;
    private String reasonToEnable;
    private String state;
    private String loginUser;
    private String nameOfficer;
    private UUID id;
    private String descriptionReason;
    private String nameUserEnabling;

    private String fullName;
    private String timeLeft;
    private String timeEnabled;
    private String enablingDateTimeStr;

}
