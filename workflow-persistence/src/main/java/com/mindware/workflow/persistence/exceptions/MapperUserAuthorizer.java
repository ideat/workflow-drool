package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.UserAuthorizer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperUserAuthorizer {
    List<UserAuthorizer> getAll();
    List<UserAuthorizer> getByCity(@Param("city") String city);
}
