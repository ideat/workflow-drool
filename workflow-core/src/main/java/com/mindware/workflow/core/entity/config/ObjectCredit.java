package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ObjectCredit {
    private UUID id;

    private String category;

    private String description;

    private Integer externalCode;

    private String code;
}
