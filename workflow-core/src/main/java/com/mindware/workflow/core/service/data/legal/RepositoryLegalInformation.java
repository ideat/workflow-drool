package com.mindware.workflow.core.service.data.legal;

import com.mindware.workflow.core.entity.legal.LegalInformation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryLegalInformation {
    Optional<LegalInformation> getByNumberRequest(Integer numberRequest);

    Optional<LegalInformation> getById(UUID id);

    List<LegalInformation> getAll();

    String getReportNumber();

    void add(LegalInformation legalInformation);

    void update(LegalInformation legalInformation);

    void delete(UUID id);
}
