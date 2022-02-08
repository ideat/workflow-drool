package com.mindware.workflow.persistence.legal.dto;

import com.mindware.workflow.core.service.data.legal.dto.LegalInformationCreditRequestDto;
import com.mindware.workflow.core.service.data.legal.dto.RepositoryLegalInformationCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryLegalInformationCreditRequestDtoMybatis implements RepositoryLegalInformationCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperLegalInformationCreditRequestDto mapper;

    RepositoryLegalInformationCreditRequestDtoMybatis(){}

    public static RepositoryLegalInformationCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryLegalInformationCreditRequestDtoMybatis repository = new RepositoryLegalInformationCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<LegalInformationCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<LegalInformationCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }
}
