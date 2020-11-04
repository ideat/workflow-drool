package com.mindware.workflow.persistence.observation;

import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.service.data.observation.RepositoryObservation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryObservationMybatis implements RepositoryObservation {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperObservation mapper;

    public static RepositoryObservation create(SqlSessionFactory sqlSessionFactory){
        RepositoryObservationMybatis repository = new RepositoryObservationMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public Optional<Observation> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    @Transactional
    public Optional<Observation> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }


    @Override
    @Transactional
    public Optional<Observation> getByNumberRequestApplicantTask(Integer numberRequest, String task) {
        return Optional.ofNullable(mapper.getByNumberRequestApplicantTask(numberRequest,task));
    }

    @Override
    @Transactional
    public List<Observation> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public void add(Observation observation) {
        mapper.add(observation);
    }

    @Override
    @Transactional
    public void update(Observation observation) {
        mapper.update(observation);
    }
}
