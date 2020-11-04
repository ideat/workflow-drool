package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class RepositoryExceptionsCreditRequestDtoMybatis implements RepositoryExceptionsCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperExceptionsCreditRequestDto mapper;

    public static RepositoryExceptionsCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryExceptionsCreditRequestDtoMybatis repository = new RepositoryExceptionsCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<ExceptionsCreditRequestDto> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }

    @Override
    @Transactional
    public List<ExceptionsCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public Optional<ExceptionsCreditRequestDto> getByCodeExceptionNumberRequest(String codeException, Integer numberRequest) {
        return Optional.ofNullable(mapper.getByCodeExceptionNumberRequest(codeException,numberRequest));
    }
}
