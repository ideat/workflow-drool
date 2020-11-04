package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement;

import java.util.List;

public interface RepositoryApplicantForStatementDto  {
    List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouse(Integer numberRequest,
                                                                           Integer numberApplicantSpouse);
    List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouseForGuarantorAndCodebtor(Integer numberRequest,
                                                                                                  Integer numberApplicant,
                                                                                                  Integer numberApplicantSpouse );
    List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouse(Integer numberRequest,
                                                                            Integer numberApplicant,
                                                                            Integer numberApplicantSpouse,
                                                                            String typeRelation);
    List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouseGuarantorCodebtor(Integer numberRequest,
                                                                            Integer numberApplicant,
                                                                            Integer numberApplicantSpouse,
                                                                            String typeRelation);
}
