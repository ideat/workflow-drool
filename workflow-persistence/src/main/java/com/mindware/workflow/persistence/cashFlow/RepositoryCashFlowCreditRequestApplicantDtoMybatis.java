package com.mindware.workflow.persistence.cashFlow;

import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlowCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestApplicantDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryCashFlowCreditRequestApplicantDtoMybatis implements RepositoryCashFlowCreditRequestApplicantDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperCashFlowCreditRequestApplicantDto mapper;

    RepositoryCashFlowCreditRequestApplicantDtoMybatis(){}

    public  static RepositoryCashFlowCreditRequestApplicantDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCashFlowCreditRequestApplicantDtoMybatis repository = new RepositoryCashFlowCreditRequestApplicantDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<CashFlowCreditRequestApplicantDto> getByLoginUser(String login) {
        return mapper.getByLoginUser(login);
    }

    @Override
    @Transactional
    public List<CashFlowCreditRequestApplicantDto> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public List<CashFlowCreditRequestApplicantDto> getByCity(String city) {
        return mapper.getByCity(city);
    }
}
