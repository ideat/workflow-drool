package com.mindware.workflow.persistence.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequestEnabled;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestEnabledApplicantDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperCreditRequestEnabled {

    void add(CreditRequestEnabled creditRequestEnabled);

    void update(CreditRequestEnabled creditRequestEnabled);

    CreditRequestEnabled getByNumberRequestOpen(@Param("numberRequest") Integer numberRequest);

    CreditRequestEnabled getById(@Param("id") UUID id);

    List<CreditRequestEnabled> getAll();

    List<CreditRequestEnabled> getByOffice(@Param("codeOffice") Integer codeOffice);

    List<CreditRequestEnabled> getByCity(@Param("city") String city);

    List<CreditRequestEnabled> getAllOpen();

}
