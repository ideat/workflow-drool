package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.Company;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryCompany {

    void addCompany(Company company);

    void updateCompany(Company company);

    Optional<Company> getCompanyByName(String name);

    Optional<Company> getCompaneyById(UUID id);
}
