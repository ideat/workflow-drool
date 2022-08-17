package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GroupScoring {

    private UUID id;

    private String evaluationName;

    private String parameterScoring; //List<ParameterScoring>

    private Integer position;

    private Integer score; //score evaluation parameter scoring
}
