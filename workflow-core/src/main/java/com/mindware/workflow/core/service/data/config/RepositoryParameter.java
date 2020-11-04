package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryParameter {
    void addParameter(Parameter parameter);

    void updateParameter(Parameter parameter);

    List<Parameter> getParametersByCategory(String category);

    List<Parameter> getAllParameters();

    List<Parameter> getAllByCategories(List<String> category);

    Optional<Parameter> getParameterById(UUID id);

    Optional<Parameter> getParameterByCategoryAndValue(String category, String value);

    void deleteParameter(UUID id);
}
