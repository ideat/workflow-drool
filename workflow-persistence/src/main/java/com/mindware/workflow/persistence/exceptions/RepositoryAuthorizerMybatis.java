package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.Authorizer;
import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryAuthorizerMybatis implements RepositoryAuthorizer {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperAuthorizer mapper;

    RepositoryAuthorizerMybatis(){}

    public static RepositoryAuthorizer create(SqlSessionFactory sqlSessionFactory){
        RepositoryAuthorizerMybatis repository = new RepositoryAuthorizerMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(Authorizer authorizer) {
        mapper.add(authorizer);
    }

    @Override
    @Transactional
    public void update(Authorizer authorizer) {
        mapper.update(authorizer);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public Optional<Authorizer> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<Authorizer> getByLoginUsers(String loginUser) {
        return Optional.ofNullable(mapper.getByLoginUsers(loginUser));
    }

    @Override
    @Transactional
    public List<Authorizer> getByScope(String scope) {
        return mapper.getByScope(scope);
    }

    @Override
    @Transactional
    public List<Authorizer> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<Authorizer> getByRiskType(String riskType) {
        return mapper.getByRiskType(riskType);
    }


}
