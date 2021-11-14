package com.mindware.workflow.core.service.data.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import com.mindware.workflow.core.entity.creditScoring.GroupScoring;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryConfigurationScoring {
    void add(ConfigurationScoring configurationScoring);

    void update(ConfigurationScoring configurationScoring);

    List<ConfigurationScoring> configurationScoringList();

    Optional<ConfigurationScoring> getByCategory(String product);

    Optional<ConfigurationScoring> getById(UUID id);



}
