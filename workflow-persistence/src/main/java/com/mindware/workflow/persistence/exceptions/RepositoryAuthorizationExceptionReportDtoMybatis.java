package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizationExceptionReportDto;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizationExceptionReportDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryAuthorizationExceptionReportDtoMybatis implements RepositoryAuthorizationExceptionReportDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperAuthorizationExceptionReportDto mapper;

    RepositoryAuthorizationExceptionReportDtoMybatis(){}

    public static RepositoryAuthorizationExceptionReportDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryAuthorizationExceptionReportDtoMybatis repository = new RepositoryAuthorizationExceptionReportDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<AuthorizationExceptionReportDto> getByNumberRequest(Integer numberRequest, String typeException) {
        return mapper.getByNumberRequest(numberRequest,typeException);
    }
}
