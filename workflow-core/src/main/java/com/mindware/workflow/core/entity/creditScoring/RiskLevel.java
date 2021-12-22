package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RiskLevel {

    private UUID id;

    private String name;

    private String expression;

    private Integer position;

    private String description;
}
