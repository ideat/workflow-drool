package com.mindware.workflow.persistence.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryCreditRequestMybatis implements RepositoryCreditRequest {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperCreditRequest mapper;

    RepositoryCreditRequestMybatis(){}

    public static RepositoryCreditRequest create(SqlSessionFactory sqlSessionFactory){
        RepositoryCreditRequestMybatis repository = new RepositoryCreditRequestMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addCreditRequest(CreditRequest creditRequest) {
        mapper.addCreditRequest(creditRequest);
    }

    @Override
    @Transactional
    public void updateCreditRequest(CreditRequest creditRequest) {
        mapper.updateCreditRequest(creditRequest);
    }

    @Override
    @Transactional
    public List<CreditRequest> getAllCreditRequest() {
        return mapper.getAllCreditRequest();
    }

    @Override
    @Transactional
    public List<CreditRequest> getCrediRequestByLoginUser(String loginUser) {
        return mapper.getCrediRequestByLoginUser(loginUser);
    }

    @Override
    @Transactional
    public List<CreditRequest> getCreditRequestByIdOffice(int idOffice) {
        return mapper.getCreditRequestByIdOffice(idOffice);
    }

    @Override
    @Transactional
    public Optional<CreditRequest> getCreditRequestById(UUID id) {
        return Optional.ofNullable(mapper.getCreditRequestById(id));
    }

    @Override
    @Transactional
    public Optional<CreditRequest> getCreditRequestByNumberRequest(Integer numberRequest) {
        return Optional.ofNullable(mapper.getCreditRequestByNumberRequest(numberRequest));
    }

    @Override
    @Transactional
    public void updateCompanySizeIndicator(CreditRequest creditRequest) {
        mapper.updateCompanySizeIndicator(creditRequest);
    }

    @Override
    @Transactional
    public void updateState(CreditRequest creditRequest) {
        mapper.updateState(creditRequest);
    }


}
