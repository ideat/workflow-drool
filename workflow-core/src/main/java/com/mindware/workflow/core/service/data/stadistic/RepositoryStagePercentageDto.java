package com.mindware.workflow.core.service.data.stadistic;

import com.mindware.workflow.core.service.data.stadistic.dto.StagesPercentageDto;

import java.util.List;
import java.util.Optional;

public interface RepositoryStagePercentageDto {
    List<StagesPercentageDto> getAllRequest();

    List<StagesPercentageDto> getAllNewRequestByYearAndMonth(String yearMonth);

    List<StagesPercentageDto> getListGroupRequestDate();

    List<StagesPercentageDto> getNewRequestByCityAndYearAndMonth(String city, String yearMonth);

    List<StagesPercentageDto> getAllDisbursedRequestByYearAndMonth(String yearMonth);

    List<StagesPercentageDto> getDisbursedRequestByCityAndYearAndMonth(String city, String yearMonth);

    List<StagesPercentageDto> getAllRejectedRequestByYearAndMonth(String yearMonth);

    List<StagesPercentageDto> getRejectedRequestByCityAndYearAndMonth(String city, String yearMonth);

    List<StagesPercentageDto> getAllInProcessRequestByYearAndMonth(String yearMonth);

    List<StagesPercentageDto> getInProcessRequestByCityAndYearAndMonth(String city, String yearMonth);

    List<StagesPercentageDto> getActiveCreditRequest();

    List<StagesPercentageDto> getFinishedCreditRequest();

    Optional<StagesPercentageDto> getTotalActiveCreditRequest();

    List<StagesPercentageDto> getApprovedCreditRequest();

    List<StagesPercentageDto> getRejectCreditRequest();

    List<StagesPercentageDto> getCreditRequestGroupedByCity();

    List<StagesPercentageDto> getCreditRequestGroupedByTypeCredit();
}
