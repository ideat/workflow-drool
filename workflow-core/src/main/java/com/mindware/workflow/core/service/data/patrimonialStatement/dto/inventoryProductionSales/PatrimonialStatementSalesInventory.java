package com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatrimonialStatementSalesInventory {
    private String fullName;

    private String activity;

    private List<ProductionSalesInventory> listInventorySales;
}
