package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.CityProvince;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperCityProvince {
    void add(CityProvince cityProvince);

    void update(CityProvince cityProvince);

    void delete(@Param("id") UUID id);

    List<CityProvince> getAll();

    CityProvince getById(@Param("id") UUID id);

    CityProvince getByCity(@Param("city") String city);

    CityProvince getByExternalCode(@Param("externalCode") Integer externalCode);
}
