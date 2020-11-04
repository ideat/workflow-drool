package com.mindware.workflow.core.service.data.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizersOfficeUserDto;

import java.util.List;

public interface RepositoryAuthorizerOfficeUserDto {
    List<AuthorizersOfficeUserDto> getByCity(String city);

    List<AuthorizersOfficeUserDto> getAll();

    List<AuthorizersOfficeUserDto> getByAmountBs(Double minimum, Double maximum);

    List<AuthorizersOfficeUserDto> getByAmountSus(Double minimum, Double maximum);
}
