package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ScoringCreditRequest {

    private UUID id;

    private Integer numberRequest;

    private String analysis; //Json List<ScoreResult>

    private Double score;

    private LocalDate analisysDate;

    private String riskOpinion;

    private Double riskResult;
}
