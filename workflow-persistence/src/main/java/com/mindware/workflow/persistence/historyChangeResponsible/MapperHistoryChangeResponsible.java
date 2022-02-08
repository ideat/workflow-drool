package com.mindware.workflow.persistence.historyChangeResponsible;

import com.mindware.workflow.core.entity.historyChangeResponsible.HistoryChangeResponsible;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperHistoryChangeResponsible {

    void add(HistoryChangeResponsible historyChangeResponsible);

    HistoryChangeResponsible getById(@Param("id") UUID id);
}
