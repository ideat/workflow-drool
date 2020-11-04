package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.TemplateContract;
import com.mindware.workflow.core.service.data.contract.RepositoryTemplateContract;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryTemplateContractMybatis implements RepositoryTemplateContract {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperTemplateContract mapper;

    RepositoryTemplateContractMybatis(){}

    public static RepositoryTemplateContract create(SqlSessionFactory sqlSessionFactory){
        RepositoryTemplateContractMybatis repository = new RepositoryTemplateContractMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(TemplateContract templateContract) {
        mapper.add(templateContract);
    }

    @Override
    @Transactional
    public void update(TemplateContract templateContract) {
        mapper.update(templateContract);
    }

    @Override
    @Transactional
    public List<TemplateContract> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<TemplateContract> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<TemplateContract> getByFileName(String fileName) {
        return Optional.ofNullable(mapper.getByFileName(fileName));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    public List<TemplateContract> getAllActive(String active) {
        return mapper.getAllActive(active);
    }


}
