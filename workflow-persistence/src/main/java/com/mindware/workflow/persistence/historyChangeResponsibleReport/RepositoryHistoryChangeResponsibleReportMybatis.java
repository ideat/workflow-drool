package com.mindware.workflow.persistence.historyChangeResponsibleReport;

import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleReport;
import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleReport;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class RepositoryHistoryChangeResponsibleReportMybatis implements RepositoryHistoryChangeResponsibleReport {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperHistoryChangeResponsibleReport mapper;

    public static RepositoryHistoryChangeResponsibleReport create (SqlSessionFactory sqlSessionFactory){
        RepositoryHistoryChangeResponsibleReportMybatis repository = new RepositoryHistoryChangeResponsibleReportMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<HistoryChangeResponsibleReport> getByCityAndRageDate(String city, LocalDate startDate, LocalDate endDate) {
        return mapper.getByCityAndRageDate(city,startDate,endDate);
    }

    @Override
    public List<HistoryChangeResponsibleReport> getByAllByRageDate(LocalDate startDate, LocalDate endDate) {
        return mapper.getByAllByRageDate(startDate,endDate);
    }

    @Override
    public List<HistoryChangeResponsibleReport> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    public List<HistoryChangeResponsibleReport> getAll() {
        return mapper.getAll();
    }
}
