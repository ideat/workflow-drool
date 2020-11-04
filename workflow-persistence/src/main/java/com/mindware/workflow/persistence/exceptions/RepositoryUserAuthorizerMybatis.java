package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryUserAuthorizer;
import com.mindware.workflow.core.service.data.exceptions.dto.UserAuthorizer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryUserAuthorizerMybatis implements RepositoryUserAuthorizer {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperUserAuthorizer mapper;

    RepositoryUserAuthorizerMybatis(){}

    public static RepositoryUserAuthorizer create(SqlSessionFactory sqlSessionFactory){
        RepositoryUserAuthorizerMybatis repository = new RepositoryUserAuthorizerMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<UserAuthorizer> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public List<UserAuthorizer> getByCity(String city) {
        return mapper.getByCity(city);
    }
}
