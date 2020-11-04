package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsApplicantCreditRequestDto;

import java.util.List;

public interface RepositoryExceptionsApplicantCreditRequestDto {
    List<ExceptionsApplicantCreditRequestDto> getAll();

    List<ExceptionsApplicantCreditRequestDto> getByUser(String user);

    List<ExceptionsApplicantCreditRequestDto> getByCity(String city);
}
