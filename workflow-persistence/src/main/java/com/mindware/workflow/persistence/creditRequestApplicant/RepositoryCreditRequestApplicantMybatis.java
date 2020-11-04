package com.mindware.workflow.persistence.creditRequestApplicant;

import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryCreditRequestApplicantMybatis implements RepositoryCreditRequestApplicant {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequestApplicant mapper;

    RepositoryCreditRequestApplicantMybatis(){}

    public static RepositoryCreditRequestApplicant create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestApplicantMybatis repository = new RepositoryCreditRequestApplicantMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(CreditRequestApplicant creditRequestApplicant) {
        mapper.add(creditRequestApplicant);
    }

    @Override
    @Transactional
    public void update(CreditRequestApplicant creditRequestApplicant) {
        mapper.update(creditRequestApplicant);
    }

    @Override
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public Optional<CreditRequestApplicant> getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(Integer idCreditRequest, Integer idApplicant, String typeRelation) {
        return Optional.ofNullable(mapper.getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(idCreditRequest,
                idApplicant ,typeRelation));
    }

    @Override
    @Transactional
    public Optional<CreditRequestApplicant> getCreditRequestApplicantbyId(UUID id) {
        return Optional.ofNullable(mapper.getCreditRequestApplicantbyId(id));
    }

    @Override
    @Transactional
    public List<CreditRequestApplicant> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }
}
