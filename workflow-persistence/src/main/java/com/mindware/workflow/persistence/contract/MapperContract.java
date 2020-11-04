package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface MapperContract {
    void add(Contract contract);

    void update(Contract contract);

    void delete(@Param("id") UUID id);

    Contract getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    Contract getById(@Param("id") UUID id);
}
