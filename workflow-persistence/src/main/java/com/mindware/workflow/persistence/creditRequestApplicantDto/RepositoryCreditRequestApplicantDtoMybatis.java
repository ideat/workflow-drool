package com.mindware.workflow.persistence.creditRequestApplicantDto;

import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryCreditRequestApplicantDtoMybatis implements RepositoryCreditRequestApplicantDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequestApplicantDto mapper;

    RepositoryCreditRequestApplicantDtoMybatis(){}

    public static RepositoryCreditRequestApplicantDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestApplicantDtoMybatis repository = new RepositoryCreditRequestApplicantDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<CreditRequestApplicantdto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<CreditRequestApplicantdto> getByLoginUser(String loginUser) {
        return mapper.getByLoginUser(loginUser);
    }

    @Override
    public List<CreditRequestApplicantdto> getByLoginUserTypeRelation(String loginUser, String typeRelation) {
        return mapper.getByLoginUserTypeRelation(loginUser,typeRelation);
    }

    @Override
    public List<CreditRequestApplicantdto> getByLoginUserNumberRequest(String loginUser, Integer numberRequest) {
        return mapper.getByLoginUserNumberRequest(loginUser,numberRequest);
    }

    @Override
    public List<CreditRequestApplicantdto> getByNumberRequestTypeRelation(Integer numberRequest, String typeRelation) {
        return mapper.getByNumberRequestTypeRelation(numberRequest,typeRelation);
    }

    @Override
    public List<CreditRequestApplicantdto> getAllByCity(String cityOffice) {
        return mapper.getAllByCity(cityOffice);
    }

    @Override
    public List<CreditRequestApplicantdto> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }
}
