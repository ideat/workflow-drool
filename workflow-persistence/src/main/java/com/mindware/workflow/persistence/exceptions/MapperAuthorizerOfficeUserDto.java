package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizersOfficeUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperAuthorizerOfficeUserDto {
    List<AuthorizersOfficeUserDto> getByCity(@Param("city") String city);

    List<AuthorizersOfficeUserDto> getAll();

    List<AuthorizersOfficeUserDto> getByAmountBs(@Param("minimumBs") Double minimum, @Param("maximumBs") Double maximum);

    List<AuthorizersOfficeUserDto> getByAmountSus(@Param("minimumSus")Double minimum, @Param("maximumSus") Double maximum);
}
