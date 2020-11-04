package com.mindware.workflow.persistence.paymentPlan.report;

import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryPaymentPlanDtoMybatis implements RepositoryPaymentPlanDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperPaymentPlanDto mapper;

    RepositoryPaymentPlanDtoMybatis(){}

    public static RepositoryPaymentPlanDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryPaymentPlanDtoMybatis repository = new RepositoryPaymentPlanDtoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<PaymentPlanDto> getDataReportPaymentPlant(int numberRequest) {
        return mapper.getDataReportPaymentPlant(numberRequest);
    }
}
