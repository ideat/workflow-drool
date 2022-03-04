package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ConfigurationScoring {

    private UUID id;

    private String category;

    private String groupScoring; //List<GroupScoring>

    private String riskLevel; //List<RiskLevel>

    private Double score;

    private Integer position;
}
