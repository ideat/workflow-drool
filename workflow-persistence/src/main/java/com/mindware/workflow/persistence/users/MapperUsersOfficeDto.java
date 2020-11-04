package com.mindware.workflow.persistence.users;

import com.mindware.workflow.core.service.data.users.dto.UsersOfficeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperUsersOfficeDto {
    List<UsersOfficeDto> getByCity(@Param("city") String city);

    List<UsersOfficeDto> getByInternalCodeOffice(@Param("internalCodeOffice") Integer internalCodeOffice);

    List<UsersOfficeDto> getByRol(@Param("rol") String rol);

    List<UsersOfficeDto> getByCityAndRol(@Param("city") String city, @Param("rol") String rol);

    List<UsersOfficeDto> getAll();
}
