package com.mindware.workflow.core.service.data.stageHistory;

import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;

import java.util.List;

public interface RepositoryStageHistoryCreditRequestDto {

    List<StageHistoryCreditRequestDto> getByUserNumberRequest(String loginUser, Integer numberRequest);

    List<StageHistoryCreditRequestDto> getByUserNumberRequestState(String loginUser, Integer numberRequest, List<String> state);

    List<StageHistoryCreditRequestDto> getByCity(String city);

    List<StageHistoryCreditRequestDto> getByUserRolState(String loginUser, String rol, List<String> state );

    List<StageHistoryCreditRequestDto> getByStateRol(List<String> state,String rol);

    List<StageHistoryCreditRequestDto> getAll();

    List<StageHistoryCreditRequestDto> getDetailByUserRolCity(String loginUser, String rol, String city);

    List<StageHistoryCreditRequestDto> getDetailByUserRol(String loginUser, String rol);

    List<StageHistoryCreditRequestDto> getDetailByNumberRequest(Integer numberRequest);

    List<StageHistoryCreditRequestDto> getGlobalDetailByCity(String city);

    List<StageHistoryCreditRequestDto> getGlobalDetailByUser(String loginUser);

    List<StageHistoryCreditRequestDto> getGlobalDetail();


}
