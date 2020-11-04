package com.mindware.workflow.persistence.stadistic;

import com.mindware.workflow.core.service.data.stadistic.dto.StagesPercentageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


@Mapper
public interface MapperStagePercentageDto {
    List<StagesPercentageDto> getAllRequest();

    List<StagesPercentageDto> getAllNewRequestByYearAndMonth(@Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getNewRequestByCityAndYearAndMonth(@Param("city") String city, @Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getAllDisbursedRequestByYearAndMonth(@Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getDisbursedRequestByCityAndYearAndMonth(@Param("city") String city, @Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getAllRejectedRequestByYearAndMonth(String yearMonth);

    List<StagesPercentageDto> getRejectedRequestByCityAndYearAndMonth(@Param("city") String city, @Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getAllInProcessRequestByYearAndMonth(@Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getInProcessRequestByCityAndYearAndMonth(@Param("city") String city, @Param("yearMonth") String yearMonth);

    List<StagesPercentageDto> getListGroupRequestDate();

    List<StagesPercentageDto> getActiveCreditRequest();

    List<StagesPercentageDto> getFinishedCreditRequest();

    StagesPercentageDto getTotalActiveCreditRequest();

    List<StagesPercentageDto> getApprovedCreditRequest();

    List<StagesPercentageDto> getRejectCreditRequest();

    List<StagesPercentageDto> getCreditRequestGroupedByCity();

}
