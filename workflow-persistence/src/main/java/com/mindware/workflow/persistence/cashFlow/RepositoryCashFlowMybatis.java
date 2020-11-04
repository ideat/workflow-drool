package com.mindware.workflow.persistence.cashFlow;

import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlow;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public class RepositoryCashFlowMybatis implements RepositoryCashFlow {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperCashFlow mapper;

    RepositoryCashFlowMybatis(){}

    public static RepositoryCashFlow create(SqlSessionFactory sqlSessionFactory){
        RepositoryCashFlowMybatis repository = new RepositoryCashFlowMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(CashFlow cashFlow) {
        mapper.add(cashFlow);
    }

    @Override
    @Transactional
    public void update(CashFlow cashFlow) {
        mapper.update(cashFlow);
    }

    @Override
    @Transactional
    public Optional<CashFlow> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    @Transactional
    public Optional<CashFlow> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
