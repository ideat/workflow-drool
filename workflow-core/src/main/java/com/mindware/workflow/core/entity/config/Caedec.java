package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Caedec {
    private UUID id;
    private String code;
    private String name;
    private String description;
}
