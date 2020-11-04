package com.mindware.workflow.core.service.data.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCreditRequest {

    void addCreditRequest(CreditRequest creditRequest);

    void updateCreditRequest(CreditRequest creditRequest);

    List<CreditRequest> getAllCreditRequest();

    List<CreditRequest> getCrediRequestByLoginUser(String loginUser);

    List<CreditRequest> getCreditRequestByIdOffice(int idOffice);

    Optional<CreditRequest> getCreditRequestById(UUID id);

    Optional<CreditRequest> getCreditRequestByNumberRequest(Integer numberRequest);

    void updateCompanySizeIndicator(CreditRequest creditRequest);

}
