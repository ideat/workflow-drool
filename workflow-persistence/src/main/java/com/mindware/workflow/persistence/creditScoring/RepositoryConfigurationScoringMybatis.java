package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryConfigurationScoring;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryConfigurationScoringMybatis implements RepositoryConfigurationScoring {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperConfigurationScoring mapper;

    RepositoryConfigurationScoringMybatis(){}

    public static RepositoryConfigurationScoring create(SqlSessionFactory sqlSessionFactory){
        RepositoryConfigurationScoringMybatis repository = new RepositoryConfigurationScoringMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ConfigurationScoring configurationScoring) {
        mapper.add(configurationScoring);
    }

    @Override
    @Transactional
    public void update(ConfigurationScoring configurationScoring) {
        mapper.update(configurationScoring);
    }

    @Override
    @Transactional
    public List<ConfigurationScoring> configurationScoringList() {
        return mapper.configurationScoringList();
    }

    @Override
    @Transactional
    public Optional<ConfigurationScoring> getByCategory(String product) {
        return Optional.ofNullable(mapper.getByCategory(product));
    }

    @Override
    @Transactional
    public Optional<ConfigurationScoring> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
