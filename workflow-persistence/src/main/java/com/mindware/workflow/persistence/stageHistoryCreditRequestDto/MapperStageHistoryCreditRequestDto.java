package com.mindware.workflow.persistence.stageHistoryCreditRequestDto;

import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperStageHistoryCreditRequestDto {
    List<StageHistoryCreditRequestDto> getByUserNumberRequest(@Param("userTask") String loginUser,
                                                              @Param("numberRequest") Integer numberRequest);

    List<StageHistoryCreditRequestDto> getByUserNumberRequestState(@Param("userTask") String loginUser,
                                                                   @Param("numberRequest") Integer numberRequest,
                                                                   @Param("state") List<String> state);

    List<StageHistoryCreditRequestDto> getByCity(@Param("city") String city);

    List<StageHistoryCreditRequestDto> getByUserRolState(@Param("userTask") String loginUser,
                                                           @Param("rol") String rol,
                                                           @Param("state") List<String> state);

    List<StageHistoryCreditRequestDto> getByStateRol(@Param("state") List<String> state,
                                                       @Param("rol") String rol);

    List<StageHistoryCreditRequestDto> getAll();

    List<StageHistoryCreditRequestDto> getDetailByUserRol(@Param("userTask") String loginUser,
                                                          @Param("rol") String rol);

    List<StageHistoryCreditRequestDto> getDetailByUserRolCity(@Param("userTask") String loginUser,
                                                          @Param("rol") String rol, @Param("city") String city);

    List<StageHistoryCreditRequestDto> getDetailByNumberRequest(@Param("numberRequest") Integer numberRequest);

    List<StageHistoryCreditRequestDto> getGlobalDetailByCity(@Param("city") String city);

    List<StageHistoryCreditRequestDto> getGlobalDetailByUser(@Param("loginUser") String loginUser);

    List<StageHistoryCreditRequestDto> getGlobalDetail();
}
