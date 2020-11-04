package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Company {

    private UUID id;

    private String name;

    private String type;

    private String ResolutionNumber;

    private String phone;

    private String address;
}
