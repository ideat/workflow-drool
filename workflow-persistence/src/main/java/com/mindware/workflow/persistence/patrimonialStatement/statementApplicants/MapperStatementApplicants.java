package com.mindware.workflow.persistence.patrimonialStatement.statementApplicants;

import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.StatementApplicants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperStatementApplicants {
    List<StatementApplicants> getStatementApplicantsByNumberRequest(@Param("numberRequest") Integer numberRequest);
    List<StatementApplicants> getStatementApplicantsByNumberApplicant(@Param("numberApplicant") Integer numberApplicant);
    List<StatementApplicants>  getTotalStatementApplicantsByNumberRequestAndGuarantee(@Param("numberRequest") Integer numberRequest);
    List<StatementApplicants> getAllStatementApplicants();
}
