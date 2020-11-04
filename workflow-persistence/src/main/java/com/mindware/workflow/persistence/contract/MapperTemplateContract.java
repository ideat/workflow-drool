package com.mindware.workflow.persistence.contract;

import com.mindware.workflow.core.entity.contract.TemplateContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperTemplateContract {
    void add(TemplateContract templateContract);

    void update(TemplateContract templateContract);

    List<TemplateContract> getAll();

    TemplateContract getById(@Param("id") UUID id);

    TemplateContract getByFileName(@Param("fileName") String fileName);

    void delete(@Param("id") UUID id);

    List<TemplateContract> getAllActive(@Param("active") String active);

}
