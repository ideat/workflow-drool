package com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales;

import com.mindware.workflow.core.entity.config.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatrimonialStatementProductionInventory {
    private String fullName;

    private String activity;

    private List<ProductionSalesInventory> listRawMaterial;

    private List<ProductionSalesInventory> listProcess;

    private List<ProductionSalesInventory> listFinished;

    private List<Resume> listResume;
}
