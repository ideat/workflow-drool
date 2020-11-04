package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.CityProvince;
import com.mindware.workflow.core.service.data.cityProvince.RepositoryCityProvince;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryCityProvinceMybatis implements RepositoryCityProvince {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCityProvince mapper;

    RepositoryCityProvinceMybatis(){}

    public static RepositoryCityProvince create(SqlSessionFactory sqlSessionFactory){
        RepositoryCityProvinceMybatis repository = new RepositoryCityProvinceMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(CityProvince cityProvince) {
        mapper.add(cityProvince);
    }

    @Override
    @Transactional
    public void update(CityProvince cityProvince) {
        mapper.update(cityProvince);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public List<CityProvince> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<CityProvince> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public Optional<CityProvince> getByCity(String city) {
        return Optional.ofNullable(mapper.getByCity(city));
    }

    @Override
    public Optional<CityProvince> getByExternalCode(Integer externalCode) {
        return Optional.ofNullable(mapper.getByExternalCode(externalCode));
    }
}
