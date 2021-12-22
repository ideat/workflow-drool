package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ParameterScoring {

    private UUID id;

    private Double value;

    private String criteria;

    private String typeCriteria;

    private String type; //Bajo, Normal

    private String description;
}
