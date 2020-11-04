package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RequestStage {
    private UUID id;

    private String stage;

    private boolean active;

    private String position;

    private Integer hours;

    private String rols;

    private String states;

//    private String activities;
//
//    private boolean isInit;
}
