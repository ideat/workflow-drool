package com.mindware.workflow.core.service.data.historyChangeResponsible;

import com.mindware.workflow.core.entity.historyChangeResponsible.HistoryChangeResponsible;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryHistoryChangeResponsible {
    void add(HistoryChangeResponsible historyChangeResponsible);

    Optional<HistoryChangeResponsible> getById(UUID id);
}
