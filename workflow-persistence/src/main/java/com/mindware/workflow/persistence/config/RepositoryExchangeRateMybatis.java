package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.ExchangeRate;
import com.mindware.workflow.core.service.data.config.RepositoryExchangeRate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryExchangeRateMybatis implements RepositoryExchangeRate {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperExchangeRate mapper;

    RepositoryExchangeRateMybatis(){}

    public static RepositoryExchangeRate create(SqlSessionFactory sqlSessionFactory){
        RepositoryExchangeRateMybatis repository = new RepositoryExchangeRateMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ExchangeRate exchangeRate) {
        mapper.add(exchangeRate);
    }

    @Override
    @Transactional
    public void update(ExchangeRate exchangeRate) {
        mapper.update(exchangeRate);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public Optional<ExchangeRate> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<ExchangeRate> getByValidityDate(LocalDate startValidity, LocalDate endValidity) {
        return Optional.ofNullable(mapper.getByValidityDate(startValidity,endValidity));
    }

    @Override
    public Optional<ExchangeRate> getActiveExchangeRateByCurrency(String currency) {
        return Optional.ofNullable(mapper.getActiveExchangeRateByCurrency(currency));
    }

    @Override
    @Transactional
    public List<ExchangeRate> getAll() {
        return mapper.getAll();
    }
}
