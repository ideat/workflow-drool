package com.mindware.workflow.persistence.templateObservation;

import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import com.mindware.workflow.core.service.data.templateObservation.RepositoryTemplateObservation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryTemplateObservationMybatis implements RepositoryTemplateObservation {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperTemplateObservation mapper;

    RepositoryTemplateObservationMybatis(){}

    public static RepositoryTemplateObservation create(SqlSessionFactory sqlSessionFactory){
        RepositoryTemplateObservationMybatis repository = new RepositoryTemplateObservationMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<TemplateObservation> templateObservationByTask(String task) {
        return mapper.templateObservationByTask(task);
    }

    @Override
    public List<TemplateObservation> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<TemplateObservation> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public void add(TemplateObservation templateObservation) {
        mapper.add(templateObservation);
    }

    @Override
    @Transactional
    public void update(TemplateObservation templateObservation) {
        mapper.update(templateObservation);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

}
