package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WorkflowProduct {

    private UUID id;

    private String codeProductCredit;

    private Integer codeObjectCredit;

    private String codeTypeCredit;

    private String requestStage; //json

    private Integer totalHours;


}
