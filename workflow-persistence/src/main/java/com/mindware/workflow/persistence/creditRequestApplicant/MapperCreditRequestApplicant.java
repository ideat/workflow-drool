package com.mindware.workflow.persistence.creditRequestApplicant;

import com.mindware.workflow.core.entity.CreditRequestApplicant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperCreditRequestApplicant {

    void add(CreditRequestApplicant creditRequestApplicant);

    void update(CreditRequestApplicant creditRequestApplicant);

    void delete(@Param("id") UUID id);

    CreditRequestApplicant getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(
            @Param("numberRequest") Integer numberRequest,
            @Param("numberApplicant") Integer numberApplicant,
            @Param("typeRelation") String typeRelation );

    CreditRequestApplicant getCreditRequestApplicantbyId(@Param("id") UUID id);

    List<CreditRequestApplicant> getByNumberRequest(@Param("numberRequest") Integer numberRequest);
}
