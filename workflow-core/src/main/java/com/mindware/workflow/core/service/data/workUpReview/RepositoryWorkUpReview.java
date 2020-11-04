package com.mindware.workflow.core.service.data.workUpReview;

import com.mindware.workflow.core.entity.workupReview.WorkUpReview;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryWorkUpReview {
    void addWorkUpReview(WorkUpReview workUpReview);

    void updateWorkUpReview(WorkUpReview workUpReview);

    List<WorkUpReview> getAllWorkUpReviews();

    List<WorkUpReview> getWorkUpReviewByNumberRequest(Integer numberRequest);

    Optional<WorkUpReview> getWorkUpReviewById(UUID id);
}
