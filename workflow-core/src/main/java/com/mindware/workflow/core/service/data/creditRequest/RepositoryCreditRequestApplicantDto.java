package com.mindware.workflow.core.service.data.creditRequest;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;

import java.util.List;
import java.util.UUID;

public interface RepositoryCreditRequestApplicantDto {
    List<CreditRequestApplicantdto>  getAll();

    List<CreditRequestApplicantdto> getByLoginUser(String loginUser);

    List<CreditRequestApplicantdto> getByLoginUserTypeRelation(String loginUser, String typeRelation);

    List<CreditRequestApplicantdto> getByLoginUserNumberRequest(String loginUser, Integer numberRequest);

    List<CreditRequestApplicantdto> getByNumberRequestTypeRelation(Integer numberRequest, String typeRelation);

    List<CreditRequestApplicantdto> getAllByCity(String cityOffice);

    List<CreditRequestApplicantdto> getByNumberRequest(Integer numberRequest);
}
