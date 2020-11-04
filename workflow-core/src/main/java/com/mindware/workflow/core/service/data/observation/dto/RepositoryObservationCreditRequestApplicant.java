package com.mindware.workflow.core.service.data.observation.dto;

import java.util.List;

public interface RepositoryObservationCreditRequestApplicant {
    List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByCity(String city);
    List<ObservationCreditRequestApplicant> getObservationCreditRequestApplicantsByUser(String login);
    List<ObservationCreditRequestApplicant> getAll();
}
