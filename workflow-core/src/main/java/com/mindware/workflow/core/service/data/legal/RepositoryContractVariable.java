package com.mindware.workflow.core.service.data.legal;

import com.mindware.workflow.core.entity.contract.ContractVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryContractVariable {
    void add(ContractVariable contractVariable);

    void update(ContractVariable contractVariable);

    void delete(UUID id);

    List<ContractVariable> getAll();

    List<ContractVariable> getByTypeVariable(String typeVariable);

    Optional<ContractVariable> getById(UUID id);

    Optional<ContractVariable> getByName(String name);

}
