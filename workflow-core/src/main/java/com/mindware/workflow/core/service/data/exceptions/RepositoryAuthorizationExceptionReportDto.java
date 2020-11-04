package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizationExceptionReportDto;

import java.util.List;

public interface RepositoryAuthorizationExceptionReportDto {

    List<AuthorizationExceptionReportDto> getByNumberRequest(Integer numberRequest, String typeException);
}
