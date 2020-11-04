package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InfoCompany {
    private UUID id;

    private String name;

    private String address;

    private String configMail; //json config mail
}
