package com.mindware.workflow.persistence.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequestEnabled;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabled;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryCreditRequestEnabledMybatis implements RepositoryCreditRequestEnabled {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequestEnabled mapper;

    RepositoryCreditRequestEnabledMybatis(){}

    public static RepositoryCreditRequestEnabled create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestEnabledMybatis repository = new RepositoryCreditRequestEnabledMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(CreditRequestEnabled creditRequestEnabled) {
        mapper.add(creditRequestEnabled);
    }

    @Override
    @Transactional
    public Optional<CreditRequestEnabled> getByNumberRequestOpen(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequestOpen(numberRequest));
    }

    @Override
    public Optional<CreditRequestEnabled> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public void update(CreditRequestEnabled creditRequestEnabled) {
        mapper.update(creditRequestEnabled);
    }

    @Override
    public List<CreditRequestEnabled> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<CreditRequestEnabled> getByOffice(Integer codeOffice) {
        return mapper.getByOffice(codeOffice);
    }

    @Override
    public List<CreditRequestEnabled> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    public List<CreditRequestEnabled> getAllOpen() {
        return mapper.getAllOpen();
    }
}
