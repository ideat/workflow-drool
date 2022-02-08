package com.mindware.workflow.persistence.legal.dto;

import com.mindware.workflow.core.service.data.legal.dto.LegalInformationCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperLegalInformationCreditRequestDto {

    List<LegalInformationCreditRequestDto> getAll();

    List<LegalInformationCreditRequestDto> getByCity(@Param("city") String city);
}
