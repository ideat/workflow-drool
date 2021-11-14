package com.mindware.workflow.persistence.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperConfigurationScoring {

    void add(ConfigurationScoring configurationScoring);

    void update(ConfigurationScoring configurationScoring);

    List<ConfigurationScoring> configurationScoringList();

    ConfigurationScoring getByCategory(@Param("category") String category);

    ConfigurationScoring getById(@Param("id") UUID id);
}
