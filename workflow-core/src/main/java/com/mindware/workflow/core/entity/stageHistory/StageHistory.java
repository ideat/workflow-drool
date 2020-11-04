package com.mindware.workflow.core.entity.stageHistory;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StageHistory {
    private UUID id;

    private Integer numberRequest;

    private String stage;

    private Instant startDateTime;

    private String state;

    private String nextState;

    private Instant initDateTime;

    private Instant finishedDateTime;

    private String userTask;//login

    private String observation;

    private String answer;

    private String comesFrom;

    private String standbyState;
}
