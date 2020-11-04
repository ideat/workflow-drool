package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.Exceptions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperExceptions {
    Exceptions getById(@Param("id") UUID id);

    Exceptions getByInternalCode(@Param("internalCode") String internalCode);

    List<Exceptions> getAll();

    void add(Exceptions exceptions);

    void update(Exceptions exceptions);

    void delete(@Param("id") UUID id);
}
