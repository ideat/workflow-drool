package com.mindware.workflow.core.service.data.contract;

import com.mindware.workflow.core.entity.contract.TemplateContract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryTemplateContract {

    void add(TemplateContract parameterContract);

    void update(TemplateContract parameterContract);

    List<TemplateContract> getAll();

    Optional<TemplateContract> getById(UUID id);

    Optional<TemplateContract> getByFileName(String fileName);

    void delete(UUID id);

    List<TemplateContract> getAllActive(String active);

}
