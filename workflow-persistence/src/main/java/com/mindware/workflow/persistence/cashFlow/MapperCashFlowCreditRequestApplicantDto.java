package com.mindware.workflow.persistence.cashFlow;

import com.mindware.workflow.core.service.data.cashFlow.dto.CashFlowCreditRequestApplicantDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperCashFlowCreditRequestApplicantDto {
    List<CashFlowCreditRequestApplicantDto> getByLoginUser(@Param("login") String login);

    List<CashFlowCreditRequestApplicantDto> getAll();

    List<CashFlowCreditRequestApplicantDto> getByCity(@Param("city") String city);
}
