package com.mindware.workflow.persistence.patrimonialStatement;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryPatrimonialStatementMybatis implements RepositoryPatrimonialStatement {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperPatrimonialStatement mapper;

    RepositoryPatrimonialStatementMybatis(){}

    public static RepositoryPatrimonialStatement create(SqlSessionFactory sqlSessionFactory){
        RepositoryPatrimonialStatementMybatis repository = new RepositoryPatrimonialStatementMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(PatrimonialStatement patrimonialStatement) {
        mapper.add(patrimonialStatement);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
         mapper.delete(id);
    }

    @Override
    @Transactional
    public void update(PatrimonialStatement patrimonialStatement) {
        mapper.update(patrimonialStatement);
    }

    @Override
    public void updateCoordinates(PatrimonialStatement patrimonialStatement) {
        mapper.updateCoordinates(patrimonialStatement);
    }

    @Override
    @Transactional
    public Optional<PatrimonialStatement> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public List<PatrimonialStatement> getByIdCreditRequestApplicantCategory(UUID idCreditRequestApplicant, String category) {
        return mapper.getByIdCreditRequestApplicantCategory(idCreditRequestApplicant,category);
    }

    @Override
    public List<PatrimonialStatement> getByIdCreditRequestApplicantCategoryElement(UUID idCreditRquestApplicant, String category, String elementCategory) {
        return mapper.getByIdCreditRequestApplicantCategoryElement(idCreditRquestApplicant,category,elementCategory);
    }

    @Override
    public List<PatrimonialStatement> getByIdCreditRequestApplicant(UUID idCreditRequestApplicant) {
        return mapper.getByIdCreditRequestApplicant(idCreditRequestApplicant);
    }
}
