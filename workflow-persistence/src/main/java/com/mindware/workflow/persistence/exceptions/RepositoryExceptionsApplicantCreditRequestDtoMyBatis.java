package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsApplicantCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsApplicantCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryExceptionsApplicantCreditRequestDtoMyBatis implements RepositoryExceptionsApplicantCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperExceptionsApplicantCreditRequestDto mapper;

    public static RepositoryExceptionsApplicantCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryExceptionsApplicantCreditRequestDtoMyBatis repository = new RepositoryExceptionsApplicantCreditRequestDtoMyBatis();
        repository.sqlSessionFactory=sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<ExceptionsApplicantCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<ExceptionsApplicantCreditRequestDto> getByUser(String user) {
        return mapper.getByUser(user);
    }

    @Override
    public List<ExceptionsApplicantCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }
}
