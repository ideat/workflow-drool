package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.ContractVariable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperContractVariable {
    void add(ContractVariable contractVariable);

    void update(ContractVariable contractVariable);

    void delete(@Param("id") UUID id);

    List<ContractVariable> getAll();

    List<ContractVariable> getByTypeVariable(@Param("typeVariable") String typeVariable);

    ContractVariable getById(@Param("id") UUID id);

    ContractVariable getByName(@Param("name") String name);
}
