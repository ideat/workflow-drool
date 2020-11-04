package com.mindware.workflow.persistence.creditRequestCompanySizeIndicatorDto;

import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestCompanySizeIndicatorDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryCreditRequestCompanySizeIndicatorDtoMybatis implements RepositoryCreditRequestCompanySizeIndicatorDto{
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequestCompanySizeIndicatorDto mapper;

    public static RepositoryCreditRequestCompanySizeIndicatorDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestCompanySizeIndicatorDtoMybatis repository = new RepositoryCreditRequestCompanySizeIndicatorDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<CreditRequestCompanySizeIndicatorDto> getByUser(String login) {
        return mapper.getByUser(login);
    }

    @Override
    public List<CreditRequestCompanySizeIndicatorDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    public List<CreditRequestCompanySizeIndicatorDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public Optional<CreditRequestCompanySizeIndicatorDto> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }
}
