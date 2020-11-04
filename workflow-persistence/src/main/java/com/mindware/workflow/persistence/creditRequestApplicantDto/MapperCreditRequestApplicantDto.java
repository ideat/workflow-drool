package com.mindware.workflow.persistence.creditRequestApplicantDto;

import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperCreditRequestApplicantDto {
    List<CreditRequestApplicantdto> getAll();

    List<CreditRequestApplicantdto> getByLoginUser(@Param("loginUser") String loginUser);

    List<CreditRequestApplicantdto> getByLoginUserTypeRelation(@Param("loginUser") String loginUser, @Param("typeRelation") String typeRelation);

    List<CreditRequestApplicantdto> getByLoginUserNumberRequest(@Param("loginUser") String loginUser, @Param("numberRequest") Integer numberRequest);

    List<CreditRequestApplicantdto> getByNumberRequestTypeRelation(@Param("numberRequest") Integer numberRequest, @Param("typeRelation") String typeRelation);

    List<CreditRequestApplicantdto> getAllByCity(@Param("cityOffice") String cityOffice);

    List<CreditRequestApplicantdto> getByNumberRequest(@Param("numberRequest") Integer numberRequest);
}
