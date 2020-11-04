package com.mindware.workflow.core.service.data.paymentPlan.report;

import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;

import java.util.List;

public interface RepositoryPaymentPlanDto {

    List<PaymentPlanDto> getDataReportPaymentPlant(int numberRequest);
}
