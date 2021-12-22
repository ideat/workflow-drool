package com.mindware.workflow.core.service.data.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringCreditRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryScoringCreditRequest {

    void add(ScoringCreditRequest scoringCreditRequest);

    void update(ScoringCreditRequest scoringCreditRequest);

    List<ScoringCreditRequest> getAll();

    Optional<ScoringCreditRequest> getByNumberRequest(Integer numberRequest);

    Optional<ScoringCreditRequest> getById(UUID id);

}
