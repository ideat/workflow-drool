package com.mindware.workflow.persistence.workUpReview;

import com.mindware.workflow.core.entity.workupReview.WorkUpReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperWorkUpReview {
    void addWorkUpReview(WorkUpReview workUpReview);

    void updateWorkUpReview(WorkUpReview workUpReview);

    List<WorkUpReview> getAllWorkUpReviews();

    List<WorkUpReview> getWorkUpReviewByNumberRequest(@Param("numberRequest") Integer numberRequest);

    WorkUpReview getWorkUpReviewById(@Param("id") UUID id);
}
