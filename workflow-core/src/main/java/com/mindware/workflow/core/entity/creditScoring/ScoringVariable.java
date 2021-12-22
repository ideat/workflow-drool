package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ScoringVariable {

    private UUID id;

    private String name;

    private String variable;

    private Double value;


}
