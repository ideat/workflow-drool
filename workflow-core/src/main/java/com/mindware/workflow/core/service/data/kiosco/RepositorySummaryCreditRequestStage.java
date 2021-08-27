package com.mindware.workflow.core.service.data.kiosco;

import com.mindware.workflow.core.entity.kiosco.SummaryCreditRequestStage;

import java.util.List;

public interface RepositorySummaryCreditRequestStage {

    List<SummaryCreditRequestStage> findActiveRequestByIdCard(String idCard);
}
