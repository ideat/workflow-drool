package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.Contract;
import com.mindware.workflow.core.service.data.contract.RepositoryContract;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public class RepositoryContractMybatis implements RepositoryContract {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperContract mapper;

    RepositoryContractMybatis(){}

    public static RepositoryContract create(SqlSessionFactory sqlSessionFactory){
        RepositoryContractMybatis repository = new RepositoryContractMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(Contract contract) {
        mapper.add(contract);
    }

    @Override
    @Transactional
    public void update(Contract contract) {
        mapper.update(contract);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public Optional<Contract> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    public Optional<Contract> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
