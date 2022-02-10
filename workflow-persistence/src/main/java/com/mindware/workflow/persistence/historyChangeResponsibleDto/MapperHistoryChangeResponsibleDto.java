package com.mindware.workflow.persistence.historyChangeResponsibleDto;

import com.mindware.workflow.core.service.data.historyChangeResponsible.dto.HistoryChangeResponsibleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperHistoryChangeResponsibleDto {

    List<HistoryChangeResponsibleDto>  getDataByRolOficial(@Param("loginUser") String loginUser);

    List<HistoryChangeResponsibleDto> getDataByRolLegal(@Param("loginUser") String loginUser);

    List<HistoryChangeResponsibleDto> getDataByRolAuthorizer(@Param("loginUser") String loginUser);

    List<HistoryChangeResponsibleDto> getDataUserWorkflow(@Param("loginUser") String loginUser);

}
