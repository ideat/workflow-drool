package com.mindware.workflow.persistence.creditResolution;

import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.UUID;
@Mapper
public interface MapperCreditResolution {
    void add(CreditResolution creditResolution);

    void update(CreditResolution creditResolution);

    CreditResolution getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    CreditResolution getById(@Param("id") UUID id);
}
