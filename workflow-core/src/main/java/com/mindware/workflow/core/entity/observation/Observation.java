package com.mindware.workflow.core.entity.observation;


import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
public class Observation {
    private UUID id;

    private Integer numberRequest;

    private String task;

    private String observations;
}
