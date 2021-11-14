package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ScoringCreditRequest {

    private UUID id;

    private Integer numberRequest;

    private ConfigurationScoring analysis;

    private Double score;

    private LocalDate analysisDate;
}
