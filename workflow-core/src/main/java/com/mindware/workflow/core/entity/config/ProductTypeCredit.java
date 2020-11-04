package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductTypeCredit {
    private UUID id;

    private Integer externalCode;

    private String description;

}
