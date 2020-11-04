package com.mindware.workflow.persistence.stageHistoryCreditRequestDto;


import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryStageHistoryCreditRequestDtoMybatis implements RepositoryStageHistoryCreditRequestDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperStageHistoryCreditRequestDto mapper;

    RepositoryStageHistoryCreditRequestDtoMybatis(){}

    public static RepositoryStageHistoryCreditRequestDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryStageHistoryCreditRequestDtoMybatis repository = new RepositoryStageHistoryCreditRequestDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getByUserNumberRequest(String loginUser, Integer numberRequest) {
        return mapper.getByUserNumberRequest(loginUser,numberRequest);
    }

    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getByUserNumberRequestState(String loginUser, Integer numberRequest, List<String> state) {
        return mapper.getByUserNumberRequestState(loginUser,numberRequest,state);
    }

    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getByCity(String city) {
        return mapper.getByCity(city);
    }

    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getByUserRolState(String loginUser, String rol, List<String> state) {
        return mapper.getByUserRolState(loginUser,rol,state);
    }

    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getByStateRol(List<String> state, String rol) {
        return mapper.getByStateRol(state,rol);
    }


    @Override
    @Transactional
    public List<StageHistoryCreditRequestDto> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<StageHistoryCreditRequestDto> getDetailByUserRolCity(String loginUser, String rol, String city) {
        return mapper.getDetailByUserRolCity(loginUser,rol,city);
    }

    @Override
    public List<StageHistoryCreditRequestDto> getDetailByUserRol(String loginUser, String rol) {
        return mapper.getDetailByUserRol(loginUser,rol);
    }

    @Override
    public List<StageHistoryCreditRequestDto> getDetailByNumberRequest(Integer numberRequest) {
        return mapper.getDetailByNumberRequest(numberRequest);
    }

    @Override
    public List<StageHistoryCreditRequestDto> getGlobalDetailByCity(String city) {
        return mapper.getGlobalDetailByCity(city);
    }

    @Override
    public List<StageHistoryCreditRequestDto> getGlobalDetailByUser(String loginUser) {
        return mapper.getGlobalDetailByUser(loginUser);
    }

    @Override
    public List<StageHistoryCreditRequestDto> getGlobalDetail() {
        return mapper.getGlobalDetail();
    }


}
