package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.WorkflowProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryWorkflowProduct {
    void add(WorkflowProduct workflowProduct);

    void delete(UUID id);

    void update(WorkflowProduct workflowProduct);

    List<WorkflowProduct> getAll();

    Optional<WorkflowProduct> getByCode(String codeTypeCredit);

    Optional<WorkflowProduct> getById(UUID id);

    Optional<WorkflowProduct> getByTypeCreditAndObject(String codeTypeCredit, Integer codeObjectCredit);
}
