package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ScoreResult {

    private String nameGroup;

    private List<ItemsScoring> itemsScoringList;

    private Double result;

    private String riskLevel;

    private Integer position;

}
