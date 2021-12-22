package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ScoringProduct {

    private UUID id;

    private String name;

    private String configurationScorings; //Json  List<ConfigurationScoring>

    private String state;

    private String riskLevel; //Json List<RiskLevel>
}
