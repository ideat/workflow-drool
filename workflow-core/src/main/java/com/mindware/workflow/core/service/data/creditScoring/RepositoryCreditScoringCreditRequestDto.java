package com.mindware.workflow.core.service.data.creditScoring;

import com.mindware.workflow.core.service.data.creditScoring.dto.CreditScoringCreditRequestDto;

import java.util.List;

public interface RepositoryCreditScoringCreditRequestDto {

    List<CreditScoringCreditRequestDto> getByLoginUser(String login);

    List<CreditScoringCreditRequestDto> getAll();

    List<CreditScoringCreditRequestDto> getByCity(String city);
}
