package com.mindware.workflow.persistence.patrimonialStatement.sworeStatement;

import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.ApplicantForStatementDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.RepositoryApplicantForStatementDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RepositoryApplicantForStatementDtoMybatis implements RepositoryApplicantForStatementDto {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperApplicantForStatementDto mapper;

    public static RepositoryApplicantForStatementDto create(SqlSessionFactory sqlSessionFactory){
        RepositoryApplicantForStatementDtoMybatis repository = new RepositoryApplicantForStatementDtoMybatis();
        repository.sqlSessionFactory  = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouse(Integer numberRequest, Integer numberApplicantSpouse) {
        return mapper.getByNumberRequestNumberApplicantSpouse(numberRequest,numberApplicantSpouse);
    }

    @Override
    public List<ApplicantForStatementDto> getByNumberRequestNumberApplicantSpouseForGuarantorAndCodebtor(Integer numberRequest
            , Integer numberApplicant , Integer numberApplicantSpouse) {
        return mapper.getByNumberRequestNumberApplicantSpouseForGuarantorAndCodebtor(numberRequest,numberApplicant, numberApplicantSpouse);
    }

    @Override
    public List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouse(Integer numberRequest,
                                                                                   Integer numberApplicant,
                                                                                   Integer numberApplicantSpouse,
                                                                                   String typeRelation) {
        return mapper.getByDataHomeVerificationApplicantSpouse(numberRequest,numberApplicant,numberApplicantSpouse,typeRelation);
    }

    @Override
    public List<ApplicantForStatementDto> getByDataHomeVerificationApplicantSpouseGuarantorCodebtor(Integer numberRequest, Integer numberApplicant, Integer numberApplicantSpouse, String typeRelation) {
        return mapper.getByDataHomeVerificationApplicantSpouseGuarantorCodebtor(numberRequest,numberApplicant,numberApplicantSpouse,typeRelation);
    }
}
