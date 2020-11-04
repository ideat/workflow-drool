package com.mindware.workflow.core.service.data.office;

import com.mindware.workflow.core.entity.config.Office;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryOffice {
    void addOffice(Office office);

    void updateOffice(Office office);

    void updateOfficeSignatorie(Office office);

    Optional<Office> getOfficeByInternalCode(int code);

    Optional<Office> getOfficeById(UUID id);

    List<Office> getAllOffices();

    List<Office> getOfficeCity();
}
