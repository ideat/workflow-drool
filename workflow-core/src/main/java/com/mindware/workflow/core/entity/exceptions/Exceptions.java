package com.mindware.workflow.core.entity.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Exceptions {
    private UUID id;

    private String internalCode;

    private String description;

    private String typeException;

    private Integer limitTime;

    private String state;

    private String riskType;

}
