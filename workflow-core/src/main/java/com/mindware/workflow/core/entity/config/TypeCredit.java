package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TypeCredit {
    private UUID id;

    private String description;

    private String externalCode;

    private String productTypeCredit; //json

    private String objectCredit;//json


}
