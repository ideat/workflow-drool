package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.TemplateForm;
import com.mindware.workflow.core.service.data.config.RepositoryTemplateForms;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryTemplateFormsMybatis implements RepositoryTemplateForms {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperTemplateForms mapper;

    public static RepositoryTemplateForms create(SqlSessionFactory sqlSessionFactory){
        RepositoryTemplateFormsMybatis repository = new RepositoryTemplateFormsMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(TemplateForm templateForm) {
        mapper.add(templateForm);
    }

    @Override
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    public void update(TemplateForm templateForm) {
        mapper.update(templateForm);
    }

    @Override
    public void updateFieldStructure(TemplateForm templateForm) {
        mapper.updateFieldStructure(templateForm);
    }


    @Override
    public Optional<TemplateForm> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public Optional<TemplateForm> getByNameCategory(String name, String category) {
        return Optional.ofNullable(mapper.getByNameCategory(name,category));
    }

    @Override
    public List<TemplateForm> getAll() {
        return mapper.getAll();
    }
}
