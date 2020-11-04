package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.TemplateForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperTemplateForms {

    void add(TemplateForm templateForm);

    void delete(@Param("id") UUID id);

    void update(TemplateForm templateForm);

    void updateFieldStructure(TemplateForm templateForm);
    
    TemplateForm getById(@Param("id") UUID id);

    TemplateForm getByNameCategory(@Param("name") String name, @Param("category") String category);

    List<TemplateForm> getAll();
}
