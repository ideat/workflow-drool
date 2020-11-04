package com.mindware.workflow.core.service.data.cashFlow;

import com.mindware.workflow.core.entity.cashFlow.CashFlow;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryCashFlow {

    void add(CashFlow cashFlow);

    void update(CashFlow cashFlow);

    Optional<CashFlow>  getByNumberRequest(Integer numberRequest);

    Optional<CashFlow> getById(UUID id);

}
