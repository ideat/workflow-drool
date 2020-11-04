package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.PatrimonialStatementSalesInventory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.ProductionSalesInventory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementSalesInventory {

    public PatrimonialStatementSalesInventory generateSalesInventory(Applicant applicant, PatrimonialStatement patrimonialStatement) throws IOException {
        PatrimonialStatementSalesInventory psi = new PatrimonialStatementSalesInventory();
        psi.setActivity(patrimonialStatement.getFieldText1());
        psi.setFullName(applicant.getFullName());
        ObjectMapper mapper = new ObjectMapper();
        List<ProductionSalesInventory> productionSalesInventoryList = mapper.readValue(patrimonialStatement.getFieldText7(),
                new TypeReference<List<ProductionSalesInventory>>(){});
        List<ProductionSalesInventory> listSalesInventory = productionSalesInventoryList.stream()
                .filter(p -> p.getTypeInventory().equals("venta"))
                .collect(Collectors.toList());

        psi.setListInventorySales(listSalesInventory);
        return psi;
    }
}
