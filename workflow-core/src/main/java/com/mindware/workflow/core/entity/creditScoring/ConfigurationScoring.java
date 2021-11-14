package com.mindware.workflow.core.entity.creditScoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ConfigurationScoring {

    private UUID id;

    private String category;

    private List<GroupScoring> groupScoring;

    private Double score;

}
