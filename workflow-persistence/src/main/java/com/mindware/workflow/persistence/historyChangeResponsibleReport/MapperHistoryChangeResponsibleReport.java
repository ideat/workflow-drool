package com.mindware.workflow.persistence.historyChangeResponsibleReport;

import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MapperHistoryChangeResponsibleReport {
    List<HistoryChangeResponsibleReport> getByCityAndRageDate(@Param("city") String city,
                                                              @Param("startDate")LocalDate startDate,
                                                              @Param("endDate")LocalDate endDate);

    List<HistoryChangeResponsibleReport> getByAllByRageDate( @Param("startDate")LocalDate startDate,
                                                             @Param("endDate")LocalDate endDate);

    List<HistoryChangeResponsibleReport> getByCity(@Param("city") String city);

    List<HistoryChangeResponsibleReport> getAll();
}
