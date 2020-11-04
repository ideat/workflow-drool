package com.mindware.workflow.core.service.data.patrimonialStatement.dto.costProduct;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatrimonialStatementCostProducts {
    private String fullName;

    private String activity;

    private List<CostProduct> costProductList;
}
