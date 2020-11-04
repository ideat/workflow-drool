package com.mindware.workflow.persistence.creditRequestCompanySizeIndicatorDto;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestCompanySizeIndicatorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperCreditRequestCompanySizeIndicatorDto {
    List<CreditRequestCompanySizeIndicatorDto> getByUser(@Param("login") String login);
    List<CreditRequestCompanySizeIndicatorDto> getByCity(@Param("city") String city);
    List<CreditRequestCompanySizeIndicatorDto> getAll();
    CreditRequestCompanySizeIndicatorDto getByNumberRequest(@Param("numberRequest") Integer numberRequest);
}
