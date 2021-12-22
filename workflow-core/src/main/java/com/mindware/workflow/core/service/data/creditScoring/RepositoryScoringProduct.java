package com.mindware.workflow.core.service.data.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryScoringProduct {

    void add(ScoringProduct scoringProduct);

    void update(ScoringProduct scoringProduct);

    Optional<ScoringProduct> getByName(String name);

    Optional<ScoringProduct> getById(UUID id);

    List<ScoringProduct> getAll();
}
