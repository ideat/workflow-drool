package com.mindware.workflow.persistence.kiosco;

import com.mindware.workflow.core.entity.kiosco.SummaryCreditRequestStage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperSummaryCreditRequestStage {

    List<SummaryCreditRequestStage> findActiveRequestByIdCard(@Param("idCard") String idCard);
}
