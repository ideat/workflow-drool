package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.UUID;

@Mapper
public interface MapperCompany {

    void addCompany(Company company);

    void updateCompany(Company company);

    Company getCompanyByName(String name);

    Company getCompanyById(UUID id);
}
