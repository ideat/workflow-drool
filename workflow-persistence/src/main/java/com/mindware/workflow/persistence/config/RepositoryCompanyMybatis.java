package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Company;
import com.mindware.workflow.core.service.data.config.RepositoryCompany;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public class RepositoryCompanyMybatis implements RepositoryCompany {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperCompany mapper;

    RepositoryCompanyMybatis(){}

    public static RepositoryCompany create(SqlSessionFactory sqlSessionFactory){
        RepositoryCompanyMybatis repository = new RepositoryCompanyMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addCompany(Company company) {
        mapper.addCompany(company);
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        mapper.updateCompany(company);
    }

    @Override
    @Transactional
    public Optional<Company> getCompanyByName(String name) {
        return Optional.ofNullable(mapper.getCompanyByName(name));
    }

    @Override
    @Transactional
    public Optional<Company> getCompaneyById(UUID id) {
        return Optional.ofNullable(mapper.getCompanyById(id));
    }
}
