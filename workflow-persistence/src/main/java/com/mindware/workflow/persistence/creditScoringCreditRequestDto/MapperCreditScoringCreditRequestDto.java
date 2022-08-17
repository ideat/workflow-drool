package com.mindware.workflow.persistence.creditScoringCreditRequestDto;

import com.mindware.workflow.core.service.data.creditScoring.dto.CreditScoringCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperCreditScoringCreditRequestDto {

    List<CreditScoringCreditRequestDto> getByLoginUser(@Param("login") String login);

    List<CreditScoringCreditRequestDto> getAll();

    List<CreditScoringCreditRequestDto> getByCity(@Param("city") String city);
}
