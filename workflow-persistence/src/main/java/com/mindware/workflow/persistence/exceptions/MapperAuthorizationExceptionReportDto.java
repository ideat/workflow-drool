package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizationExceptionReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperAuthorizationExceptionReportDto {

    List<AuthorizationExceptionReportDto> getByNumberRequest(@Param("numberRequest") Integer numberRequest
                                                            ,@Param("typeException") String typeException);
}
