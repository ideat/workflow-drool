package com.mindware.workflow.persistence.observation.dto;

import com.mindware.workflow.core.service.data.observation.dto.ObservationCreditRequestApplicant;
import com.mindware.workflow.core.service.data.observation.dto.RepositoryObservationCreditRequestApplicant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryObservationCreditRequestApplicantMybatis implements RepositoryObservationCreditRequestApplicant {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperObservationCreditRequestApplicant mapper;

    public static RepositoryObservationCreditRequestApplicant create(SqlSessionFactory sqlSessionFactory){
        RepositoryObservationCreditRequestApplicantMybatis repository = new RepositoryObservationCreditRequestApplicantMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByCity(String city) {
        return mapper.getObservationCreditRequestApplicantsByCity(city);
    }

    @Override
    @Transactional
    public List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByUser(String login) {
        return mapper.getObservationCreditRequestApplicantsByUser(login);
    }

    @Override
    public List<ObservationCreditRequestApplicant> getAll() {
        return mapper.getAll();
    }
}
