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

    private List<ParameterScoring> parameterScoring;

    private Integer position;

    private String score; //score evaluation parameter scoring
}
