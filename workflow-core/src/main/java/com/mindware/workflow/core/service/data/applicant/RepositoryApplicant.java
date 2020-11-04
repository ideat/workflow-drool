package com.mindware.workflow.core.service.data.applicant;

import com.mindware.workflow.core.entity.Applicant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryApplicant {

    void addApplicant(Applicant applicant);

    void updateApplicant(Applicant applicant);

    List<Applicant> getAllApplicant();

    List<Applicant> getApplicantByLoginUser(String loginUser);

    Optional<Applicant> getApplicantById(UUID id);

    Optional<Applicant> getApplicantByIdCard(String idCard);

    Optional<Applicant> getApplicantByNumberApplicant(Integer numberApplicant);

}
