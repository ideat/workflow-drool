package com.mindware.workflow.persistence.workUpReview;

import com.mindware.workflow.core.entity.workupReview.WorkUpReview;
import com.mindware.workflow.core.service.data.workUpReview.RepositoryWorkUpReview;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryWorkUpReviewMybatis implements RepositoryWorkUpReview {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperWorkUpReview mapper;

    RepositoryWorkUpReviewMybatis(){}

    public static RepositoryWorkUpReview create(SqlSessionFactory sqlSessionFactory){
        RepositoryWorkUpReviewMybatis repository = new RepositoryWorkUpReviewMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addWorkUpReview(WorkUpReview workUpReview) {
        mapper.addWorkUpReview(workUpReview);
    }

    @Override
    @Transactional
    public void updateWorkUpReview(WorkUpReview workUpReview) {
        mapper.updateWorkUpReview(workUpReview);
    }

    @Override
    @Transactional
    public List<WorkUpReview> getAllWorkUpReviews() {
        return mapper.getAllWorkUpReviews();
    }

    @Override
    @Transactional
    public List<WorkUpReview> getWorkUpReviewByNumberRequest(Integer numberRequest) {
        return mapper.getWorkUpReviewByNumberRequest(numberRequest);
    }

    @Override
    @Transactional
    public Optional<WorkUpReview> getWorkUpReviewById(UUID id) {
        return Optional.ofNullable(mapper.getWorkUpReviewById(id));
    }
}
