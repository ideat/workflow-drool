package com.mindware.workflow.core.service.data.stageHistory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class StageHistoryCreditRequestDto {
    private UUID idStageHistory;

    private Integer numberRequest;

    private Integer numberApplicant;

    private String firstName;

    private String lastName;

    private Double amount;

    private String currency;

    private String userTask;//login user create creditrequest

    private Instant startDateTime;

    private Instant initDateTime;

    private Instant finishedDateTime;

    private Integer timeElapsed;

    private Integer timeToBeAssigned;

    private String city;

    private String productCredit;

    private Integer totalHours;

    private Integer totalHoursStage;

    private Integer hoursLeft;

    private String stage;

    private Integer productCode;

    private String state;

    private String codeTypeCredit;

    private Integer codeObjectCredit;

    public String getFullName(){
        return Optional.ofNullable(this.lastName).orElse("")+" "
                +Optional.ofNullable(this.firstName).orElse("");
    }
}
