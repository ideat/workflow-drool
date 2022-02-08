package com.mindware.workflow.persistence.creditRequestEnabledApplicantDto;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestEnabledApplicantDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

@Mapper
public interface MapperCreditRequestEnabledApplicantDto {

    List<CreditRequestEnabledApplicantDto> getAll();

    List<CreditRequestEnabledApplicantDto> getAllByCity(@Param("cityOffice") String cityOffice);

    List<CreditRequestEnabledApplicantDto> getByEnablingUser(@Param("enablingUser")  String enablingUser);

    List<CreditRequestEnabledApplicantDto> getByLoginUser(@Param("loginUser") String loginUser);

    List<CreditRequestEnabledApplicantDto> getAllEnabled();

    List<CreditRequestEnabledApplicantDto> getAllEnabledByCity(@Param("cityOffice") String cityOffice);

    List<CreditRequestEnabledApplicantDto> getAllEnabledByEnablingUser(@Param("enablingUser") String enablingUser);

    List<CreditRequestEnabledApplicantDto> getEnabledReport(@Param("cityOffice") String cityOffice,
                                                            @Param("fromDate") Instant fromDate,
                                                            @Param("toDate") Instant toDate);
}
