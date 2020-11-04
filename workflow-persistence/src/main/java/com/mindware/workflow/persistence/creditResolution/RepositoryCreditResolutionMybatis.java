package com.mindware.workflow.persistence.creditResolution;

import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public class RepositoryCreditResolutionMybatis implements RepositoryCreditResolution {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditResolution mapper;

    RepositoryCreditResolutionMybatis(){}

    public static RepositoryCreditResolution create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditResolutionMybatis repository = new RepositoryCreditResolutionMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(CreditResolution creditResolution) {
        mapper.add(creditResolution);
    }

    @Override
    @Transactional
    public void update(CreditResolution creditResolution) {
        mapper.update(creditResolution);
    }

    @Override
    @Transactional
    public Optional<CreditResolution> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    public Optional<CreditResolution> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
