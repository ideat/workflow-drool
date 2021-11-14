package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class GroupScoring {

    private UUID id;

    private Map<String, List<ParameterScoring>> parameterScoring;

    private Map<String,String> clasification; //name,range (Bajo, 16-20)

    private Double result;

}
