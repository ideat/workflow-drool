package com.mindware.workflow.core.service.data.cityProvince;

import com.mindware.workflow.core.entity.config.CityProvince;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCityProvince {

    void add(CityProvince cityProvince);

    void update(CityProvince cityProvince);

    void delete(UUID id);

    List<CityProvince> getAll();

    Optional<CityProvince> getById(UUID id);

    Optional<CityProvince> getByCity(String city);

    Optional<CityProvince> getByExternalCode(Integer externalCode);
}
