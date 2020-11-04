package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizerExceptionsCreditRequestDto;

import java.util.List;

public interface RepositoryAuthorizerExceptionCreditRequestDto {

    List<AuthorizerExceptionsCreditRequestDto> getByCity(String city);

    List<AuthorizerExceptionsCreditRequestDto> getAll();

    List<AuthorizerExceptionsCreditRequestDto> getByUser(String loginUser);

    List<AuthorizerExceptionsCreditRequestDto> getByCityCurrencyAmounts(String city, String currency, Double miminum, Double maximum);

    List<AuthorizerExceptionsCreditRequestDto> getByCurrencyAmounts(String currency, Double minimum, Double maximum);

    List<AuthorizerExceptionsCreditRequestDto> getByRiskType(List<String> riskType);

    List<AuthorizerExceptionsCreditRequestDto> getByRiskTypeCity(List<String> riskType, String city);
}
