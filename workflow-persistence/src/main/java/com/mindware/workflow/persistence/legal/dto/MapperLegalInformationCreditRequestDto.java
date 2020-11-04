package com.mindware.workflow.persistence.legal.dto;

import com.mindware.workflow.core.service.data.legal.dto.LegalInformationCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapperLegalInformationCreditRequestDto {

    List<LegalInformationCreditRequestDto> getAll();
}
