package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizerOfficeUserDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizersOfficeUserDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryAuthorizerOfficeUserDtoMybatis implements RepositoryAuthorizerOfficeUserDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperAuthorizerOfficeUserDto mapper;

    RepositoryAuthorizerOfficeUserDtoMybatis(){}

    public static RepositoryAuthorizerOfficeUserDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryAuthorizerOfficeUserDtoMybatis repository = new RepositoryAuthorizerOfficeUserDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<AuthorizersOfficeUserDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    @Transactional
    public List<AuthorizersOfficeUserDto> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public List<AuthorizersOfficeUserDto> getByAmountBs(Double minimum, Double maximum) {
        return mapper.getByAmountBs(minimum,maximum);
    }

    @Override
    @Transactional
    public List<AuthorizersOfficeUserDto> getByAmountSus(Double minimum, Double maximum) {
        return mapper.getByAmountSus(minimum,maximum);
    }
}
