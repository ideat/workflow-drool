package com.mindware.workflow.persistence.stadistic;

import com.mindware.workflow.core.service.data.stadistic.dto.StagesPercentageDto;
import com.mindware.workflow.core.service.data.stadistic.RepositoryStagePercentageDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryStagePercentageDtoMybatis implements RepositoryStagePercentageDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperStagePercentageDto mapper;

    public static RepositoryStagePercentageDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryStagePercentageDtoMybatis repository = new RepositoryStagePercentageDtoMybatis();
        repository.sqlSessionFactory=sqlSessionFactory;
        return repository;
    }


    @Override
    public List<StagesPercentageDto> getAllRequest() {
        return mapper.getAllRequest();
    }

    @Override
    public List<StagesPercentageDto> getAllNewRequestByYearAndMonth(String yearMonth) {
        return mapper.getAllNewRequestByYearAndMonth(yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getListGroupRequestDate() {
        return mapper.getListGroupRequestDate();
    }

    @Override
    public List<StagesPercentageDto> getNewRequestByCityAndYearAndMonth(String city, String yearMonth) {
        return mapper.getNewRequestByCityAndYearAndMonth(city,yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getAllDisbursedRequestByYearAndMonth(String yearMonth) {
        return mapper.getAllDisbursedRequestByYearAndMonth(yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getDisbursedRequestByCityAndYearAndMonth(String city, String yearMonth) {
        return mapper.getDisbursedRequestByCityAndYearAndMonth(city,yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getAllRejectedRequestByYearAndMonth(String yearMonth) {
        return mapper.getAllRejectedRequestByYearAndMonth(yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getRejectedRequestByCityAndYearAndMonth(String city, String yearMonth) {
        return mapper.getRejectedRequestByCityAndYearAndMonth(city,yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getAllInProcessRequestByYearAndMonth(String yearMonth) {
        return mapper.getAllInProcessRequestByYearAndMonth(yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getInProcessRequestByCityAndYearAndMonth(String city, String yearMonth) {
        return mapper.getInProcessRequestByCityAndYearAndMonth(city,yearMonth);
    }

    @Override
    public List<StagesPercentageDto> getActiveCreditRequest() {
        return mapper.getActiveCreditRequest();
    }

    @Override
    public List<StagesPercentageDto> getFinishedCreditRequest() {
        return mapper.getFinishedCreditRequest();
    }

    @Override
    public Optional<StagesPercentageDto> getTotalActiveCreditRequest() {
        return Optional.ofNullable(mapper.getTotalActiveCreditRequest());
    }

    @Override
    public List<StagesPercentageDto> getApprovedCreditRequest() {
        return mapper.getApprovedCreditRequest();
    }

    @Override
    public List<StagesPercentageDto> getRejectCreditRequest() {
        return mapper.getRejectCreditRequest();
    }

    @Override
    public List<StagesPercentageDto> getCreditRequestGroupedByCity() {
        return mapper.getCreditRequestGroupedByCity();
    }


}
