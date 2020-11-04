package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.entity.exceptions.Authorizer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryAuthorizer {
    void add(Authorizer authorizer);

    void update(Authorizer authorizer);

    void delete(UUID id);

    Optional<Authorizer> getById(UUID id);

    Optional<Authorizer> getByLoginUsers(String loginUser);

    List<Authorizer> getByScope(String scope);

    List<Authorizer> getAll();

    List<Authorizer> getByRiskType(String riskType);

}
