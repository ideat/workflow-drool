package com.mindware.workflow.persistence.legal;

import com.mindware.workflow.core.entity.legal.LegalInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperLegalInformation {
    LegalInformation getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    LegalInformation getById(@Param("id") UUID id);

    List<LegalInformation> getAll();

    String getReportNumber();

    void add(LegalInformation legalInformation);

    void update(LegalInformation legalInformation);

    void updateUser(LegalInformation legalInformation);

    void delete(@Param("id") UUID id);
}
