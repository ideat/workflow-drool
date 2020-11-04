package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.entity.exceptions.Exceptions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryExceptions {
    Optional<Exceptions> getById(UUID id);

    Optional<Exceptions> getByInternalCode(String internalCode);

    List<Exceptions> getAll();

    void add(Exceptions exceptions);

    void update(Exceptions exceptions);

    void delete(UUID id);
}
