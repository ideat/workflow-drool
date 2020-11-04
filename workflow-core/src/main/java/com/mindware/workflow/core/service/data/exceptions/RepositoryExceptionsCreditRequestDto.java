package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsCreditRequestDto;

import java.util.List;
import java.util.Optional;

public interface RepositoryExceptionsCreditRequestDto {
    List<ExceptionsCreditRequestDto> getByNumberRequest(Integer numberRequest);
    List<ExceptionsCreditRequestDto> getAll();
    Optional<ExceptionsCreditRequestDto> getByCodeExceptionNumberRequest(String codeException, Integer numberRequest);
}
