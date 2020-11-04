package com.mindware.workflow.core.service.data.observation;

import com.mindware.workflow.core.entity.observation.Observation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryObservation {
    Optional<Observation> getByNumberRequest(Integer numberRequest);

    Optional<Observation> getById(UUID id);

    Optional<Observation> getByNumberRequestApplicantTask(Integer numberRequest, String task);

    List<Observation> getAll();

    void add(Observation observation);

    void update(Observation observation);

}
