package com.mindware.workflow.persistence.observation.dto;

import com.mindware.workflow.core.service.data.observation.dto.ObservationCreditRequestApplicant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperObservationCreditRequestApplicant {
    List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByCity(@Param("city")String city);

    List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByUser(@Param("login") String login);

    List<ObservationCreditRequestApplicant> getAll();
}
