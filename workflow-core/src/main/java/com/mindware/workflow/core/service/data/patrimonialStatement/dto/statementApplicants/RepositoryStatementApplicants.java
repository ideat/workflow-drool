package com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants;

import java.util.List;

public interface RepositoryStatementApplicants {
    List<StatementApplicants>  getStatementApplicantsByNumberRequest(Integer numberRequest);
    List<StatementApplicants>  getTotalStatementApplicantsByNumberRequestAndGuarantee(Integer numberRequest);
    List<StatementApplicants> getStatementApplicantsByNumberApplicant(Integer numberApplicant);
    List<StatementApplicants> getAllStatementApplicants();

}
