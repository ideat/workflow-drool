package com.mindware.workflow.persistence.creditRequestEnabledApplicantDto;

import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabledApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestEnabledApplicantDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

public class RepositoryCreditRequestEnabledApplicantDtoMybatis implements com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabledApplicantDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequestEnabledApplicantDto mapper;

    public static RepositoryCreditRequestEnabledApplicantDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestEnabledApplicantDtoMybatis repository = new RepositoryCreditRequestEnabledApplicantDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getAllByCity(String cityOffice) {
        return mapper.getAllByCity(cityOffice);
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getByEnablingUser(String enablingUser) {
        return mapper.getByEnablingUser(enablingUser);
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getByLoginUser(String loginUser) {
        return mapper.getByLoginUser(loginUser);
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getAllEnabled() {
        return mapper.getAllEnabled();
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getAllEnabledByCity(String cityOffice) {
        return mapper.getAllEnabledByCity(cityOffice);
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getAllEnabledByEnablignUser(String enablingUser) {
        return mapper.getAllEnabledByEnablingUser(enablingUser);
    }

    @Override
    public List<CreditRequestEnabledApplicantDto> getEnabledReport(String cityOffice, Instant fromDate, Instant toDate) {
        return mapper.getEnabledReport(cityOffice,fromDate,toDate);
    }
}
