package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryWorkflowProductMybatis implements RepositoryWorkflowProduct {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperWorkflowProduct mapper;

    RepositoryWorkflowProductMybatis(){}

    public static RepositoryWorkflowProduct create(SqlSessionFactory sqlSessionFactory){
        RepositoryWorkflowProductMybatis repository = new RepositoryWorkflowProductMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(WorkflowProduct workflowProduct) {
        mapper.add(workflowProduct);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public void update(WorkflowProduct workflowProduct) {
        mapper.update(workflowProduct);
    }

    @Override
    @Transactional
    public List<WorkflowProduct> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<WorkflowProduct> getByCode(String codeTypeCredit) {
        return Optional.ofNullable(mapper.getByCode(codeTypeCredit));
    }

    @Override
    public Optional<WorkflowProduct> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public Optional<WorkflowProduct> getByTypeCreditAndObject(String codeTypeCredit, Integer codeObjectCredit) {
        return Optional.ofNullable(mapper.getByTypeCreditAndObject(codeTypeCredit,codeObjectCredit));
    }
}
