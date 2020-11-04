package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.service.data.contract.dto.ContractCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperContractCreditRequestDto {
    List<ContractCreditRequestDto> getAll();
    List<ContractCreditRequestDto> getByCity(@Param("city") String city);
    List<ContractCreditRequestDto> getByUser(@Param("loginUser") String loginUser);
}
