package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.ExceptionsApplicantCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperExceptionsApplicantCreditRequestDto {

    List<ExceptionsApplicantCreditRequestDto> getAll();

    List<ExceptionsApplicantCreditRequestDto> getByUser(@Param("user")String user);

    List<ExceptionsApplicantCreditRequestDto> getByCity(@Param("city") String city);
}
