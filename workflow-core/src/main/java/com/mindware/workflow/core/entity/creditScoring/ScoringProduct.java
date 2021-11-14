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

    private List<ConfigurationScoring> configurationScorings;

    private Double result;

    private String state;
}
