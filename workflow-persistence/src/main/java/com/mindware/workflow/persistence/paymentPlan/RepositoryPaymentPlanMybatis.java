package com.mindware.workflow.persistence.paymentPlan;

import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryPaymentPlanMybatis implements RepositoryPaymentPlan {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperPaymentPlan mapper;

    RepositoryPaymentPlanMybatis(){}

    public static RepositoryPaymentPlan create(SqlSessionFactory sqlSessionFactory){
        RepositoryPaymentPlanMybatis repository = new RepositoryPaymentPlanMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(PaymentPlan paymentPlan) {
        mapper.add(paymentPlan);
    }

    @Override
    public void delete(int numberRequest) {
        mapper.delete(numberRequest);
    }

    @Override
    public List<PaymentPlan> getPaymentPlanByNumberRequest(int numberRequest) {

        return mapper.getPaymentPlanByNumberRequest(numberRequest);
    }
}
