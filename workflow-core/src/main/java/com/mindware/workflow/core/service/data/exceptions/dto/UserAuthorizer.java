package com.mindware.workflow.core.service.data.exceptions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserAuthorizer {
    private UUID id;
    private String loginUser;
    private String city;
    private String fullName;
    private String scope;
    private String stateAuthorizer;
    private String stateUser;
}
