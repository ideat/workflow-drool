package com.mindware.workflow.core.service.data.legal.dto;

import java.util.List;

public interface RepositoryLegalInformationCreditRequestDto {

    List<LegalInformationCreditRequestDto> getAll();

    List<LegalInformationCreditRequestDto> getByCity(String city);
}
