package com.mindware.workflow.core.service.data.historyChangeResponsible;

import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleDto;

import java.util.List;

public interface RepositoryHistoryChangeResponsibleDto {

    List<HistoryChangeResponsibleDto>  getDataByRolOficial(String loginUser);

    List<HistoryChangeResponsibleDto> getDataByRolLegal(String loginUser);

    List<HistoryChangeResponsibleDto> getDataByRolAuthorizer(String loginUser);

    List<HistoryChangeResponsibleDto> getDataUserWorkflow(String loginUser);
}
