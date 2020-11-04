package com.mindware.workflow.core.service.data.creditResolution;

import com.mindware.workflow.core.entity.creditResolution.CreditResolution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCreditResolution {
    void add(CreditResolution creditResolution);

    void update(CreditResolution creditResolution);

    Optional<CreditResolution> getByNumberRequest(Integer numberRequest);

    Optional<CreditResolution> getById(UUID id);

}
