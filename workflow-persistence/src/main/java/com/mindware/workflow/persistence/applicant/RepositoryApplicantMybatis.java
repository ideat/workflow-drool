package com.mindware.workflow.persistence.applicant;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryApplicantMybatis implements RepositoryApplicant {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperApplicant mapper;

    RepositoryApplicantMybatis(){}

    public static RepositoryApplicant create(SqlSessionFactory sqlSessionFactory){
        RepositoryApplicantMybatis repository = new RepositoryApplicantMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addApplicant(Applicant applicant) {
        mapper.addApplicant(applicant);
    }

    @Override
    public void updateApplicant(Applicant applicant) {
        mapper.updateApplicant(applicant);
    }

    @Override
    public List<Applicant> getAllApplicant() {
        return mapper.getAllApplicant();
    }

    @Override
    public List<Applicant> getApplicantByLoginUser(String loginUser) {
        return mapper.getApplicantByLoginUser(loginUser);
    }

    @Override
    public Optional<Applicant> getApplicantById(UUID id) {
        return Optional.ofNullable(mapper.getApplicantById(id));
    }

    @Override
    public Optional<Applicant> getApplicantByIdCard(String idCard) {
        return Optional.ofNullable(mapper.getApplicantByIdCard(idCard));
    }

    @Override
    public Optional<Applicant> getApplicantByNumberApplicant(Integer numberApplicant) {
        return Optional.ofNullable(mapper.getApplicantByNumberApplicant(numberApplicant));
    }

}
