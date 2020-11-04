package com.mindware.workflow.core.service.data.users;

import com.mindware.workflow.core.service.data.users.dto.UsersOfficeDto;

import java.util.List;

public interface RepositoryUsersOfficeDto {

    List<UsersOfficeDto> getByCity(String city);

    List<UsersOfficeDto> getByInternalCodeOffice(Integer internalCodeOffice);

    List<UsersOfficeDto> getByRol(String rol);

    List<UsersOfficeDto> getByCityAndRol(String city, String rol);

    List<UsersOfficeDto> getAll();

}
