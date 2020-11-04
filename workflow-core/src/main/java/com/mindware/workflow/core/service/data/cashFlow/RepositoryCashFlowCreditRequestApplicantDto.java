package com.mindware.workflow.core.service.data.cashFlow;

import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestApplicantDto;

import java.util.List;

public interface RepositoryCashFlowCreditRequestApplicantDto {
    List<CashFlowCreditRequestApplicantDto> getByLoginUser(String login);

    List<CashFlowCreditRequestApplicantDto> getAll();

    List<CashFlowCreditRequestApplicantDto> getByCity(String city);
}
