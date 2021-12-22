package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemsScoring {
    private UUID id;

    private String concept;

    private ConfigurationScoring value;

    private Double score;

    private Integer position;
}
