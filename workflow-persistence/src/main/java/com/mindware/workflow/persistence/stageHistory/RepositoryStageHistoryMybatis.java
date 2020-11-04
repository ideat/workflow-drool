package com.mindware.workflow.persistence.stageHistory;

import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryStageHistoryMybatis implements RepositoryStageHistory {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperStageHistory mapper;

    RepositoryStageHistoryMybatis(){}

    public static RepositoryStageHistory create(SqlSessionFactory sqlSessionFactory){
        RepositoryStageHistoryMybatis repository = new RepositoryStageHistoryMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(StageHistory stageHistory) {
        mapper.add(stageHistory);
    }

    @Override
    @Transactional
    public void update(StageHistory stageHistory) {
        mapper.update(stageHistory);
    }

    @Override
    @Transactional
    public List<StageHistory> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public List<StageHistory> getByNumberRequestStageState(String stage, Integer numberRequest, String state) {
        return mapper.getByNumberRequestStageState(stage,numberRequest,state);
    }

    @Override
    @Transactional
    public List<StageHistory> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }

    @Override
    @Transactional
    public Optional<StageHistory> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
