package com.mindware.workflow.core.entity.legal;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Owners {
    private UUID id;

    private String fullName;

    private String idCardComplete;

    private String maritalStatus;
}
