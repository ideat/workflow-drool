package com.mindware.workflow.core.service.data.creditRequestApplicant;

import com.mindware.workflow.core.entity.CreditRequestApplicant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCreditRequestApplicant {
    void add(CreditRequestApplicant creditRequestApplicant);

    void update(CreditRequestApplicant creditRequestApplicant);

    void delete(UUID id);

    Optional<CreditRequestApplicant> getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(Integer numberRequest, Integer numberApplicant, String typeRelation);

    Optional<CreditRequestApplicant> getCreditRequestApplicantbyId(UUID id);

    List<CreditRequestApplicant> getByNumberRequest(Integer numberRequest);


}
