package com.mindware.workflow.persistence.observation;

import com.mindware.workflow.core.entity.observation.Observation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperObservation {
    Observation getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    Observation getById(@Param("id") UUID id);

    Observation getByNumberRequestApplicantTask(@Param("numberRequest") Integer numberRequest,
                                                @Param("task") String task);

    List<Observation> getAll();

    void add(Observation observation);

    void update(Observation observation);
}
