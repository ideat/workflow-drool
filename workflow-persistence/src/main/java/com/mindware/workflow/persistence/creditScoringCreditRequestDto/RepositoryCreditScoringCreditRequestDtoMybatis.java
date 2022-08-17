package com.mindware.workflow.persistence.creditScoringCreditRequestDto;

import com.mindware.workflow.core.service.data.creditScoring.RepositoryCreditScoringCreditRequestDto;
import com.mindware.workflow.core.service.data.creditScoring.dto.CreditScoringCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryCreditScoringCreditRequestDtoMybatis implements RepositoryCreditScoringCreditRequestDto {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperCreditScoringCreditRequestDto mapper;

    RepositoryCreditScoringCreditRequestDtoMybatis(){}

    public static RepositoryCreditScoringCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditScoringCreditRequestDtoMybatis repository = new RepositoryCreditScoringCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<CreditScoringCreditRequestDto> getByLoginUser(String login) {
        return mapper.getByLoginUser(login);
    }

    @Override
    public List<CreditScoringCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<CreditScoringCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }
}
