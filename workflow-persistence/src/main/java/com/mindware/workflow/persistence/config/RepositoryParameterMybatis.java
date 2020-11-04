package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryParameterMybatis implements RepositoryParameter {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperParameter mapper;

    RepositoryParameterMybatis(){}

    public static RepositoryParameter create(SqlSessionFactory sqlSessionFactory){
        RepositoryParameterMybatis repository = new RepositoryParameterMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addParameter(Parameter parameter) {
        mapper.addParameter(parameter);
    }

    @Override
    @Transactional
    public void updateParameter(Parameter parameter) {
        mapper.updateParameter(parameter);
    }

    @Override
    @Transactional
    public List<Parameter> getParametersByCategory(String category) {
        return mapper.getParametersByCategory(category);
    }

    @Override
    @Transactional
    public List<Parameter> getAllParameters() {
        return mapper.getAllParameters();
    }

    @Override
    @Transactional
    public List<Parameter> getAllByCategories(@Param("category") List<String> category) {
        return mapper.getAllByCategories(category);
    }

    @Override
    @Transactional
    public Optional<Parameter> getParameterById(UUID id) {
        return Optional.ofNullable(mapper.getParameterById(id));
    }

    @Override
    @Transactional
    public Optional<Parameter> getParameterByCategoryAndValue(String category, String value) {
        return Optional.ofNullable(mapper.getParameterByCategoryAndValue(category,value));
    }

    @Override
    @Transactional
    public void deleteParameter(UUID id) {
        mapper.deleteParameter(id);
    }
}
