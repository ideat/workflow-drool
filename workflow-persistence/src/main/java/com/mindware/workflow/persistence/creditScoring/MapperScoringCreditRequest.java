package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringCreditRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperScoringCreditRequest {

    void add(ScoringCreditRequest scoringCreditRequest);

    void update(ScoringCreditRequest scoringCreditRequest);

    ScoringCreditRequest getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    ScoringCreditRequest getById(@Param("id") UUID id);
}
