package com.mindware.workflow.persistence.office;

import com.mindware.workflow.core.entity.config.Office;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperOffice {
    void addOffice(Office office);

    void updateOffice(Office office);

    void updateOfficeSignatorie(Office office);

    Office getOfficeByInternalCode(@Param("internalCode") int code);

    Office getOfficeById(@Param("id")UUID id);

    List<Office> getAllOffices();

    List<Office> getOfficeCity();
}
