package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperScoringProduct {

    void add(ScoringProduct scoringProduct);

    void update(ScoringProduct scoringProduct);

    ScoringProduct getByName(@Param("name") String name);

    ScoringProduct getById(@Param("id") UUID id);

    List<ScoringProduct> getAll();
}
