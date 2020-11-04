package com.mindware.workflow.core.service.data.contract;

import com.mindware.workflow.core.entity.contract.Contract;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryContract {
    void add(Contract contract);

    void update(Contract contract);

    void delete(UUID id);

    Optional<Contract> getByNumberRequest(Integer numberRequest);

    Optional<Contract> getById(UUID id);
}
