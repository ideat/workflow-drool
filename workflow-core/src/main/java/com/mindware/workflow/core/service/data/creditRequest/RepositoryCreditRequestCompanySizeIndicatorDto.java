package com.mindware.workflow.core.service.data.creditRequest;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestCompanySizeIndicatorDto;

import java.util.List;
import java.util.Optional;

public interface RepositoryCreditRequestCompanySizeIndicatorDto {
    List<CreditRequestCompanySizeIndicatorDto> getByUser(String login);
    List<CreditRequestCompanySizeIndicatorDto> getByCity(String city);
    List<CreditRequestCompanySizeIndicatorDto> getAll();
    Optional<CreditRequestCompanySizeIndicatorDto>  getByNumberRequest(Integer numberRequest);
}
