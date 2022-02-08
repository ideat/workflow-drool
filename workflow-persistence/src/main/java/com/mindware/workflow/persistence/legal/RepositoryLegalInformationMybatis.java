package com.mindware.workflow.persistence.legal;

import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryLegalInformationMybatis implements RepositoryLegalInformation {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperLegalInformation mapper;

    RepositoryLegalInformationMybatis(){}

    public static RepositoryLegalInformation create(SqlSessionFactory sqlSessionFactory){
        RepositoryLegalInformationMybatis repository = new RepositoryLegalInformationMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public Optional<LegalInformation> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    @Transactional
    public Optional<LegalInformation> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public List<LegalInformation> getAll() {
        return mapper.getAll();
    }

    @Override
    public String getReportNumber() {
        return mapper.getReportNumber();
    }

    @Override
    @Transactional
    public void add(LegalInformation legalInformation) {
        mapper.add(legalInformation);
    }

    @Override
    @Transactional
    public void update(LegalInformation legalInformation) {
        mapper.update(legalInformation);
    }

    @Override
    public void updateUser(LegalInformation legalInformation) {
        mapper.updateUser(legalInformation);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }
}
