package com.mindware.workflow.core.service.data.creditRequest;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestEnabledApplicantDto;

import java.time.Instant;
import java.util.List;

public interface RepositoryCreditRequestEnabledApplicantDto {

    List<CreditRequestEnabledApplicantDto> getAll();

    List<CreditRequestEnabledApplicantDto> getAllByCity(String cityOffice);

    List<CreditRequestEnabledApplicantDto> getByEnablingUser(String enablingUser);

    List<CreditRequestEnabledApplicantDto> getByLoginUser(String loginUser);

    List<CreditRequestEnabledApplicantDto> getAllEnabled();

    List<CreditRequestEnabledApplicantDto> getAllEnabledByCity(String cityOffice);

    List<CreditRequestEnabledApplicantDto> getAllEnabledByEnablignUser(String enablingUser);

    List<CreditRequestEnabledApplicantDto> getEnabledReport(String cityOffice, Instant fromDate, Instant toDate);
}
