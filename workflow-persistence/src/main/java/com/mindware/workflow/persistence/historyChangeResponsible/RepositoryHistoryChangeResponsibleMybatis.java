package com.mindware.workflow.persistence.historyChangeResponsible;

import com.mindware.workflow.core.entity.historyChangeResponsible.HistoryChangeResponsible;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsible;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class RepositoryHistoryChangeResponsibleMybatis implements RepositoryHistoryChangeResponsible {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperHistoryChangeResponsible mapper;

    public static RepositoryHistoryChangeResponsible create(SqlSessionFactory sqlSessionFactory){
        RepositoryHistoryChangeResponsibleMybatis repository = new RepositoryHistoryChangeResponsibleMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public void add(HistoryChangeResponsible historyChangeResponsible) {
        mapper.add(historyChangeResponsible);
    }

    @Override
    public Optional<HistoryChangeResponsible> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
