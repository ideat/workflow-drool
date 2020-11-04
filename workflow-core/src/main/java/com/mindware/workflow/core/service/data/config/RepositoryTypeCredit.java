package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.TypeCredit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryTypeCredit {
    void add(TypeCredit typeCredit);

    void update(TypeCredit typeCredit);

    List<TypeCredit> getAll();

    Optional<TypeCredit> getByExternalCode(String externalCode);

    Optional<TypeCredit> getById(UUID id);


}
