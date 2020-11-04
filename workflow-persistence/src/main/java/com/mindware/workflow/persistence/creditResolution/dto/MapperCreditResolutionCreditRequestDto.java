package com.mindware.workflow.persistence.creditResolution.dto;

import com.mindware.workflow.core.service.data.creditResolution.dto.CreditResolutionCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperCreditResolutionCreditRequestDto {
    List<CreditResolutionCreditRequestDto> getByLogin(@Param("login") String login);

    List<CreditResolutionCreditRequestDto> getByCity(@Param("city") String city);

    List<CreditResolutionCreditRequestDto> getAll();

}
