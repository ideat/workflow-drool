package com.mindware.workflow.persistence.patrimonialStatement.sworeStatement;

import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.ApplicantForStatementDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface  MapperApplicantForStatementDto {
    List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouse(@Param("numberRequest") Integer numberRequest,
                                                      @Param("numberApplicantSpouse") Integer numberApplicantSpouse);

    List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouseForGuarantorAndCodebtor(@Param("numberRequest") Integer numberRequest,
                                                                                                  @Param("numberApplicant") Integer numberApplicant,
                                                                                                  @Param("numberApplicantSpouse") Integer numberApplicantSpouse);

    List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouse(@Param("numberRequest") Integer numberRequest,
                                                                            @Param("numberApplicant") Integer numberApplicant,
                                                                            @Param("numberApplicantSpouse") Integer numberApplicantSpouse,
                                                                            @Param("typeRelation") String typeRelation);

    List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouseGuarantorCodebtor(@Param("numberRequest") Integer numberRequest,
                                                                            @Param("numberApplicant") Integer numberApplicant,
                                                                            @Param("numberApplicantSpouse") Integer numberApplicantSpouse,
                                                                            @Param("typeRelation") String typeRelation);
}
