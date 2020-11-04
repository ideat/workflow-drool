package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryExceptionsCreditRequest {
    void add(ExceptionsCreditRequest exceptionsCreditRequest);

    void update(ExceptionsCreditRequest exceptionsCreditRequest);

    void delete(UUID id);

    List<ExceptionsCreditRequest> getByNumberRequest(Integer numberRequest);

    List<ExceptionsCreditRequest> getAll();

    Optional<ExceptionsCreditRequest> getById(UUID id);

    Optional<ExceptionsCreditRequest> getByCodeExceptionNumberRequest(String codeException, Integer numberRequest);
}
