package com.mindware.workflow.persistence.paymentPlan;

import com.mindware.workflow.core.entity.PaymentPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperPaymentPlan {
    void add(PaymentPlan paymentPlan);

    void delete(int numberRequest);

    List<PaymentPlan> getPaymentPlanByNumberRequest(@Param("numberRequest") int numberRequest);

}
