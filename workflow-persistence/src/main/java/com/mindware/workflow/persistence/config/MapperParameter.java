package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Parameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperParameter {
    void addParameter(Parameter parameter);

    void updateParameter(Parameter parameter);

    List<Parameter> getParametersByCategory(@Param("category") String category);

    List<Parameter> getAllByCategories(@Param("category") List<String> category);

    List<Parameter> getAllParameters();

    Parameter getParameterById(@Param("id") UUID id);

    Parameter getParameterByCategoryAndValue( @Param("category") String category, @Param("value") String value);

    void deleteParameter(@Param("id") UUID id);
}
