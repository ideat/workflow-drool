package com.mindware.workflow.persistence.templateObservation;

import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperTemplateObservation {
    List<TemplateObservation> templateObservationByTask(@Param("task") String task);

    List<TemplateObservation> getAll();

    TemplateObservation getById(@Param("id") UUID id);

    void add(TemplateObservation templateObservation);

    void update(TemplateObservation templateObservation);

    void delete(@Param("id") UUID id);

}
