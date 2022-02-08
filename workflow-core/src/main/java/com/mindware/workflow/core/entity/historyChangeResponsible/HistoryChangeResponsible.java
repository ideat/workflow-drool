package com.mindware.workflow.core.entity.historyChangeResponsible;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class HistoryChangeResponsible {
    private UUID id;

    private Integer numberRequest;

    private LocalDate dateChange;

    private String rolName;

    private String oldResponsible;

    private String newResponsible;

    private String reasonChangeResponsible;

    private String processedBy;
}
