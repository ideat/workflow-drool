package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.ContractVariable;
import com.mindware.workflow.core.service.data.legal.RepositoryContractVariable;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryContractVariableMybatis implements RepositoryContractVariable {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperContractVariable mapper;

    RepositoryContractVariableMybatis(){}

    public static RepositoryContractVariable create(SqlSessionFactory sqlSessionFactory){
        RepositoryContractVariableMybatis repository = new RepositoryContractVariableMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ContractVariable contractVariable) {
        mapper.add(contractVariable);
    }

    @Override
    @Transactional
    public void update(ContractVariable contractVariable) {
        mapper.update(contractVariable);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public List<ContractVariable> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<ContractVariable> getByTypeVariable(String typeVariable) {
        return mapper.getByTypeVariable(typeVariable);
    }

    @Override
    @Transactional
    public Optional<ContractVariable> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<ContractVariable> getByName(String name) {
        return Optional.ofNullable(mapper.getByName(name));
    }
}
