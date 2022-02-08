package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequest;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryExceptionsCreditRequestMybatis implements RepositoryExceptionsCreditRequest {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperExceptionsCreditRequest mapper;

    RepositoryExceptionsCreditRequestMybatis(){}

    public static RepositoryExceptionsCreditRequest create(SqlSessionFactory sqlSessionFactory){
        RepositoryExceptionsCreditRequestMybatis repository = new RepositoryExceptionsCreditRequestMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ExceptionsCreditRequest exceptionsCreditRequest) {
        mapper.add(exceptionsCreditRequest);
    }

    @Override
    @Transactional
    public void update(ExceptionsCreditRequest exceptionsCreditRequest) {
        mapper.update(exceptionsCreditRequest);
    }

    @Override
    public void updateUser(ExceptionsCreditRequest exceptionsCreditRequest) {
        mapper.updateUser(exceptionsCreditRequest);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public List<ExceptionsCreditRequest> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }

    @Override
    @Transactional
    public List<ExceptionsCreditRequest> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<ExceptionsCreditRequest> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<ExceptionsCreditRequest> getByCodeExceptionNumberRequest(String codeException, Integer numberRequest) {
        return Optional.ofNullable(mapper.getByCodeExceptionNumberRequest(codeException,numberRequest));
    }
}
