package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperExceptionsCreditRequest {
    void add(ExceptionsCreditRequest exceptionsCreditRequest);

    void update(ExceptionsCreditRequest exceptionsCreditRequest);

    void delete(@Param("id") UUID id);

    List<ExceptionsCreditRequest> getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    List<ExceptionsCreditRequest> getAll();

    ExceptionsCreditRequest getById(@Param("id") UUID id);

    ExceptionsCreditRequest getByCodeExceptionNumberRequest(@Param("codeException") String codeException,@Param("numberRequest") Integer numberRequest);
}
