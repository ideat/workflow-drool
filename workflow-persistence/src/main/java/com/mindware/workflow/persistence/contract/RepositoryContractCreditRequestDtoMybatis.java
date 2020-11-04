package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.service.data.contract.RepositoryContractCreditRequestDto;
import com.mindware.workflow.core.service.data.contract.dto.ContractCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryContractCreditRequestDtoMybatis implements com.mindware.workflow.core.service.data.contract.RepositoryContractCreditRequestDto {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperContractCreditRequestDto mapper;

    RepositoryContractCreditRequestDtoMybatis(){}

    public static RepositoryContractCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryContractCreditRequestDtoMybatis repository = new RepositoryContractCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<ContractCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public List<ContractCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    @Transactional
    public List<ContractCreditRequestDto> getByUser(String loginUser) {
        return mapper.getByUser(loginUser);
    }
}
