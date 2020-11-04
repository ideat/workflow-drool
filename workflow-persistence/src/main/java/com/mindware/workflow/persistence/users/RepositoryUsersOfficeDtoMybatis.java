package com.mindware.workflow.persistence.users;

import com.mindware.workflow.core.service.data.users.RepositoryUsersOfficeDto;
import com.mindware.workflow.core.service.data.users.dto.UsersOfficeDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryUsersOfficeDtoMybatis implements RepositoryUsersOfficeDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperUsersOfficeDto mapper;

    RepositoryUsersOfficeDtoMybatis(){}

    public static RepositoryUsersOfficeDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryUsersOfficeDtoMybatis repository = new RepositoryUsersOfficeDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<UsersOfficeDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    @Transactional
    public List<UsersOfficeDto> getByInternalCodeOffice(Integer internalCodeOffice) {
        return mapper.getByInternalCodeOffice(internalCodeOffice);
    }

    @Override
    @Transactional
    public List<UsersOfficeDto> getByRol(String rol) {
        return mapper.getByRol(rol);
    }

    @Override
    public List<UsersOfficeDto> getByCityAndRol(String city, String rol) {
        return mapper.getByCityAndRol(city,rol);
    }

    @Override
    public List<UsersOfficeDto> getAll() {
        return mapper.getAll();
    }
}
