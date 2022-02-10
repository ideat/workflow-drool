package com.mindware.workflow.persistence.historyChangeResponsibleDto;

import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleDto;
import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryHistoryChangeResponsibleDtoMybatis implements RepositoryHistoryChangeResponsibleDto {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperHistoryChangeResponsibleDto mapper;

    public static RepositoryHistoryChangeResponsibleDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryHistoryChangeResponsibleDtoMybatis repository = new RepositoryHistoryChangeResponsibleDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<HistoryChangeResponsibleDto> getDataByRolOficial(String loginUser) {
        return mapper.getDataByRolOficial(loginUser);
    }

    @Override
    public List<HistoryChangeResponsibleDto> getDataByRolLegal(String loginUser) {
        return mapper.getDataByRolLegal(loginUser);
    }

    @Override
    public List<HistoryChangeResponsibleDto> getDataByRolAuthorizer(String loginUser) {
        return mapper.getDataByRolAuthorizer(loginUser);
    }

    @Override
    public List<HistoryChangeResponsibleDto> getDataUserWorkflow(String loginUser) {
        return mapper.getDataUserWorkflow(loginUser);
    }

}
