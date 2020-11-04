package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MapperExceptionsCreditRequestDto {
    List<ExceptionsCreditRequestDto> getByNumberRequest(@Param("numberRequest") Integer numberRequest);
    List<ExceptionsCreditRequestDto> getAll();
    ExceptionsCreditRequestDto getByCodeExceptionNumberRequest(@Param("codeException") String codeException
            , @Param("numberRequest") Integer numberRequest);
}
