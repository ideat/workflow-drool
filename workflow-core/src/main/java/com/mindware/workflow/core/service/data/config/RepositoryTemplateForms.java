package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.TemplateForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryTemplateForms {
    void add(TemplateForm templateForm);

    void delete(UUID id);

    void update(TemplateForm templateForm);

    void updateFieldStructure(TemplateForm templateForm);
    
    Optional<TemplateForm> getById(UUID id);

    Optional<TemplateForm> getByNameCategory(String name, String category);

    List<TemplateForm> getAll();

}
