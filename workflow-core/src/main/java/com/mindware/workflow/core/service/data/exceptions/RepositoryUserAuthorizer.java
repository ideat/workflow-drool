package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.UserAuthorizer;

import java.util.List;

public interface RepositoryUserAuthorizer {

    List<UserAuthorizer> getAll();
    List<UserAuthorizer> getByCity(String city);
}
