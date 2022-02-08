package com.mindware.workflow.core.service.data.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequestEnabled;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCreditRequestEnabled {

    void add(CreditRequestEnabled creditRequestEnabled);

    Optional<CreditRequestEnabled> getByNumberRequestOpen(Integer numberRequest);

    Optional<CreditRequestEnabled> getById(UUID id);

    void update(CreditRequestEnabled creditRequestEnabled);

    List<CreditRequestEnabled> getAll();

    List<CreditRequestEnabled> getByOffice(Integer codeOffice);

    List<CreditRequestEnabled> getByCity(String city);

    List<CreditRequestEnabled> getAllOpen();
}
