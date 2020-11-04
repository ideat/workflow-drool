package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CityProvince {
    private UUID id;

    private String city;

    private String provinces;

    private Integer externalCode;
}
