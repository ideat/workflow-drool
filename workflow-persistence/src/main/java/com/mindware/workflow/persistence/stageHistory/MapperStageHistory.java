package com.mindware.workflow.persistence.stageHistory;

import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperStageHistory {
    void add(StageHistory stageHistory);

    void update(StageHistory stageHistory);

    List<StageHistory> getAll();

    List<StageHistory> getByNumberRequestStageState(@Param("stage") String stage,
                                                    @Param("numberRequest") Integer numberRequest,
                                                    @Param("state") String state);

    List<StageHistory> getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    StageHistory getById(@Param("id") UUID id);
}
