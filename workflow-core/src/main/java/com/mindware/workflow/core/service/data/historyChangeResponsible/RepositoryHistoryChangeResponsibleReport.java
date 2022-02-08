package com.mindware.workflow.core.service.data.historyChangeResponsible;

import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleReport;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryHistoryChangeResponsibleReport {

    List<HistoryChangeResponsibleReport> getByCityAndRageDate(String city, LocalDate startDate, LocalDate endDate);

    List<HistoryChangeResponsibleReport> getByAllByRageDate(LocalDate startDate, LocalDate endDate);

    List<HistoryChangeResponsibleReport> getByCity(String city);

    List<HistoryChangeResponsibleReport> getAll();
}
