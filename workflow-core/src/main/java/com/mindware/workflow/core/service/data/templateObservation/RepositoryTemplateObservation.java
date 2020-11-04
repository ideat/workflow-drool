package com.mindware.workflow.core.service.data.templateObservation;

import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryTemplateObservation {

    List<TemplateObservation> templateObservationByTask(String task);

    List<TemplateObservation> getAll();

    Optional<TemplateObservation> getById(UUID id);

    void add(TemplateObservation templateObservation);

    void update(TemplateObservation templateObservation);

    void delete(UUID id);


}
