package com.mindware.workflow.persistence.cashFlow;

import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperCashFlow {

    void add(CashFlow cashFlow);

    void update(CashFlow cashFlow);

    CashFlow getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    CashFlow getById(@Param("id") UUID id);
}
