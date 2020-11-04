package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizerExceptionCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizerExceptionsCreditRequestDto;
import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class RepositoryAuthorizerExceptionCreditRequestDtoMybatis implements RepositoryAuthorizerExceptionCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperAuthorizerExceptionCreditRequestDto mapper;

    RepositoryAuthorizerExceptionCreditRequestDtoMybatis(){}

    public static RepositoryAuthorizerExceptionCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryAuthorizerExceptionCreditRequestDtoMybatis repository = new RepositoryAuthorizerExceptionCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<AuthorizerExceptionsCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    @Transactional
    public List<AuthorizerExceptionsCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<AuthorizerExceptionsCreditRequestDto> getByUser(String loginUser) {
        return mapper.getByUser(loginUser);
    }

    @Override
    public List<AuthorizerExceptionsCreditRequestDto> getByCityCurrencyAmounts(String city, String currency, Double miminum, Double maximum) {
        return mapper.getByCityCurrencyAmounts(city,currency,miminum,maximum);
    }

    @Override
    public List<AuthorizerExceptionsCreditRequestDto> getByCurrencyAmounts(String currency, Double minimum, Double maximum) {
        return mapper.getByCurrencyAmounts(currency,minimum,maximum);
    }

    @Override
    public List<AuthorizerExceptionsCreditRequestDto> getByRiskType(List<String> riskType) {
        return mapper.getByRiskType(riskType);
    }

    @Override
    public List<AuthorizerExceptionsCreditRequestDto> getByRiskTypeCity(List<String> riskType, String city) {
        return mapper.getByRiskTypeCity(riskType,city);
    }
}
