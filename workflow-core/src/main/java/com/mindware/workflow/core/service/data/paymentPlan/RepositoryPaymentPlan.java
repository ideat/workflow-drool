package com.mindware.workflow.core.service.data.paymentPlan;

import com.mindware.workflow.core.entity.PaymentPlan;

import java.util.List;

public interface RepositoryPaymentPlan {

    void add(PaymentPlan paymentPlan);

    void delete(int numberRequest);

    List<PaymentPlan> getPaymentPlanByNumberRequest(int numberRequest);



}
