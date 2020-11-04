package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.TypeCredit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperTypeCredit {

    void add(TypeCredit typeCredit);

    void update(TypeCredit typeCredit);

    List<TypeCredit> getAll();

    TypeCredit getByExternalCode(@Param("externalCode") String externalCode);

    TypeCredit getById(@Param("id") UUID id);
}
