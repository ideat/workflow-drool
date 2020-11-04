package com.mindware.workflow.core.service.data.contract;

import com.mindware.workflow.core.service.data.contract.dto.ContractCreditRequestDto;

import java.util.List;

public interface RepositoryContractCreditRequestDto {

    List<ContractCreditRequestDto> getAll();
    List<ContractCreditRequestDto> getByCity(String city);
    List<ContractCreditRequestDto> getByUser(String loginUser);
}
