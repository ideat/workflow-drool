package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {

    private UUID id;

    private String name;

    private String interesRate;

    private String documentsRequirements;

    private String state;

}
