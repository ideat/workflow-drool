package com.mindware.workflow.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreditRequestApplicant {
    private UUID id;
    private Integer numberRequest;
    private Integer numberApplicant;
    private String typeRelation;
}
