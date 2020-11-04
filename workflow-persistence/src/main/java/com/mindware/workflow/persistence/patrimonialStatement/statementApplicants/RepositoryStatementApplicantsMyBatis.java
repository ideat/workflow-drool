package com.mindware.workflow.persistence.patrimonialStatement.statementApplicants;

import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.RepositoryStatementApplicants;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.StatementApplicants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryStatementApplicantsMyBatis implements RepositoryStatementApplicants {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperStatementApplicants mapper;

    RepositoryStatementApplicantsMyBatis(){}

    public static RepositoryStatementApplicants create(SqlSessionFactory sqlSessionFactory){
        RepositoryStatementApplicantsMyBatis repository = new RepositoryStatementApplicantsMyBatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<StatementApplicants> getStatementApplicantsByNumberRequest(Integer numberRequest) {
        return mapper.getStatementApplicantsByNumberRequest(numberRequest);
    }

    @Override
    public List<StatementApplicants> getTotalStatementApplicantsByNumberRequestAndGuarantee(Integer numberRequest) {
        return mapper.getTotalStatementApplicantsByNumberRequestAndGuarantee(numberRequest);
    }

    @Override
    @Transactional
    public List<StatementApplicants> getStatementApplicantsByNumberApplicant(Integer numberApplicant) {
        return mapper.getStatementApplicantsByNumberApplicant(numberApplicant);
    }

    @Override
    @Transactional
    public List<StatementApplicants> getAllStatementApplicants() {
        return mapper.getAllStatementApplicants();
    }
}
