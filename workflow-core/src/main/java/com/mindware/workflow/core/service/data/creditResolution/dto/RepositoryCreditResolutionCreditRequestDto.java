package com.mindware.workflow.core.service.data.creditResolution.dto;

import java.util.List;

public interface RepositoryCreditResolutionCreditRequestDto {
    List<CreditResolutionCreditRequestDto> getByLogin(String login);

    List<CreditResolutionCreditRequestDto> getByCity(String city);

    List<CreditResolutionCreditRequestDto> getAll();
}
