package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.Authorizer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperAuthorizer {
    void add(Authorizer authorizer);

    void update(Authorizer authorizer);

    void delete(UUID id);

    Authorizer getById(@Param("id") UUID id);

    Authorizer getByLoginUsers(@Param("loginUser") String loginUser);

    List<Authorizer> getByScope(@Param("scope") String scope);

    List<Authorizer> getAll();

    List<Authorizer> getByRiskType(@Param("riskType") String riskType);

}
