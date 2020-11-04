package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.Exceptions;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptions;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryExceptionsMybatis implements RepositoryExceptions {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperExceptions mapper;

    RepositoryExceptionsMybatis(){}

    public static RepositoryExceptions create(SqlSessionFactory sqlSessionFactory){
        RepositoryExceptionsMybatis repository = new RepositoryExceptionsMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public Optional<Exceptions> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<Exceptions> getByInternalCode(String internalCode) {
        return Optional.ofNullable(mapper.getByInternalCode(internalCode));
    }

    @Override
    @Transactional
    public List<Exceptions> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public void add(Exceptions exceptions) {
        mapper.add(exceptions);
    }

    @Override
    @Transactional
    public void update(Exceptions exceptions) {
        mapper.update(exceptions);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }
}
