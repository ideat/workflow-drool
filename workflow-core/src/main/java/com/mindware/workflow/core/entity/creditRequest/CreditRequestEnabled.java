package com.mindware.workflow.core.entity.creditRequest;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class CreditRequestEnabled {
    private UUID id;

    private Integer numberRequest;

    private Instant enabledDateTime;

    private Instant finishedDateTime;

    private Integer hoursEnabled;

    private String enablingUser;

    private String reasonToEnable;

    private String descriptionReason;

}
