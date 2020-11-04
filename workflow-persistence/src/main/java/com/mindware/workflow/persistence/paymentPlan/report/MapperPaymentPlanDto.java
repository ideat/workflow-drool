package com.mindware.workflow.persistence.paymentPlan.report;

import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperPaymentPlanDto {

    List<PaymentPlanDto> getDataReportPaymentPlant(@Param("numberRequest") int numberRequest);
}
