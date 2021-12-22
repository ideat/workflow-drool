package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringProduct;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringProduct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryScoringProductMybatis implements RepositoryScoringProduct {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperScoringProduct mapper;

    RepositoryScoringProductMybatis(){}

    public static RepositoryScoringProduct create(SqlSessionFactory sqlSessionFactory){
        RepositoryScoringProductMybatis repository =  new RepositoryScoringProductMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ScoringProduct scoringProduct) {
        mapper.add(scoringProduct);
    }

    @Override
    @Transactional
    public void update(ScoringProduct scoringProduct) {
        mapper.update(scoringProduct);
    }

    @Override
    public Optional<ScoringProduct> getByName(String name) {
        return Optional.ofNullable(mapper.getByName(name));
    }

    @Override
    public Optional<ScoringProduct> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public List<ScoringProduct> getAll() {
        return mapper.getAll();
    }
}
