package com.mindware.workflow.persistence.creditResolution.dto;

import com.mindware.workflow.core.service.data.creditResolution.dto.CreditResolutionCreditRequestDto;
import com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryCreditResolutionCreditRequestDtoMybatis implements com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditResolutionCreditRequestDto mapper;

    RepositoryCreditResolutionCreditRequestDtoMybatis(){}

    public static RepositoryCreditResolutionCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditResolutionCreditRequestDtoMybatis repository = new RepositoryCreditResolutionCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<CreditResolutionCreditRequestDto> getByLogin(String login) {
        return mapper.getByLogin(login);
    }

    @Override
    public List<CreditResolutionCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    public List<CreditResolutionCreditRequestDto> getAll() {
        return mapper.getAll();
    }
}
