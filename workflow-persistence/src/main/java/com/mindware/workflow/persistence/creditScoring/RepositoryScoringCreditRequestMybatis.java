package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringCreditRequest;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringCreditRequest;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryScoringCreditRequestMybatis implements RepositoryScoringCreditRequest {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperScoringCreditRequest mapper;

    RepositoryScoringCreditRequestMybatis(){}

    public static RepositoryScoringCreditRequest create(SqlSessionFactory sqlSessionFactory){
        RepositoryScoringCreditRequestMybatis repository = new RepositoryScoringCreditRequestMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ScoringCreditRequest scoringCreditRequest) {
        mapper.add(scoringCreditRequest);
    }

    @Override
    public void update(ScoringCreditRequest scoringCreditRequest) {
        mapper.update(scoringCreditRequest);
    }

    @Override
    public List<ScoringCreditRequest> getAll() {
        return null;
    }

    @Override
    public Optional<ScoringCreditRequest> getByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getByNumberRequest(numberRequest));
    }

    @Override
    public Optional<ScoringCreditRequest> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
