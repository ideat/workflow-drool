package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FieldsStructure {

    private String componentName;

    private String componentLabel;

    private boolean visible;

    private Integer position;

    private boolean required;

    private String values;
}
