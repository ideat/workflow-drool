package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperWorkflowProduct {
    void add(WorkflowProduct workflowProduct);

    void delete(UUID id);

    void update(WorkflowProduct workflowProduct);

    List<WorkflowProduct> getAll();

    WorkflowProduct getByCode(@Param("codeProductCredit") String codeProductCredit);

    WorkflowProduct getById(@Param("id") UUID id);

   WorkflowProduct getByTypeCreditAndObject(@Param("codeTypeCredit") String codeTypeCredit, @Param("codeObjectCredit") Integer codeObjectCredit);
}
