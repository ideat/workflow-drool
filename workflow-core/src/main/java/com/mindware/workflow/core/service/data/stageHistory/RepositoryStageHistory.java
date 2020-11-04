package com.mindware.workflow.core.service.data.stageHistory;

import com.mindware.workflow.core.entity.stageHistory.StageHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryStageHistory {
    void add(StageHistory stageHistory);

    void update(StageHistory stageHistory);

    List<StageHistory> getAll();

    List<StageHistory> getByNumberRequestStageState(String stage, Integer numberRequest, String state);

    List<StageHistory> getByNumberRequest(Integer numberRequest);

    Optional<StageHistory> getById(UUID id);


}
