package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryTypeCreditMybatis implements RepositoryTypeCredit {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperTypeCredit mapper;

    RepositoryTypeCreditMybatis(){}

    public static RepositoryTypeCredit create(SqlSessionFactory sqlSessionFactory){
        RepositoryTypeCreditMybatis repository = new RepositoryTypeCreditMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(TypeCredit typeCredit) {
        mapper.add(typeCredit);
    }

    @Override
    @Transactional
    public void update(TypeCredit typeCredit) {
        mapper.update(typeCredit);
    }

    @Override
    @Transactional
    public List<TypeCredit> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<TypeCredit> getByExternalCode(String externalCode) {
        return Optional.ofNullable(mapper.getByExternalCode(externalCode));
    }

    @Override
    public Optional<TypeCredit> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
