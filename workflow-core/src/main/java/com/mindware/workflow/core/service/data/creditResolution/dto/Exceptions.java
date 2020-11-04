package com.mindware.workflow.core.service.data.creditResolution.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Exceptions {
    private UUID id;

    private String politicalNumber;

    private String description;
}
