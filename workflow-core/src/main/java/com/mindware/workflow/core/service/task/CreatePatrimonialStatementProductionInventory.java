package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.PatrimonialStatementProductionInventory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.ProductionSalesInventory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.Resume;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementProductionInventory {

    public PatrimonialStatementProductionInventory generateProductionInventory(Applicant applicant, PatrimonialStatement patrimonialStatement) throws IOException {
        PatrimonialStatementProductionInventory ppi = new PatrimonialStatementProductionInventory();
        ppi.setActivity(patrimonialStatement.getFieldText1());
        ppi.setFullName(applicant.getFullName());
        ObjectMapper mapper  = new ObjectMapper();
        List<ProductionSalesInventory> productionSalesInventoryList = mapper.readValue(patrimonialStatement.getFieldText7(),
                new TypeReference<List<ProductionSalesInventory>>(){});
        List<ProductionSalesInventory> listRawMaterial = productionSalesInventoryList.stream()
                .filter(p -> p.getTypeInventory().equals("insumo"))
                .collect(Collectors.toList());
        List<ProductionSalesInventory> listProcess = productionSalesInventoryList.stream()
                .filter(p -> p.getTypeInventory().equals("proceso"))
                .collect(Collectors.toList());
        List<ProductionSalesInventory> listFinished = productionSalesInventoryList.stream()
                .filter(p -> p.getTypeInventory().equals("terminado"))
                .collect(Collectors.toList());

        ppi.setListRawMaterial(listRawMaterial);
        ppi.setListProcess(listProcess);
        ppi.setListFinished(listFinished);
        ppi.setListResume(createResume(listRawMaterial,listProcess,listFinished));
        return ppi;
    }

    private List<Resume> createResume(List<ProductionSalesInventory> listRawMaterial,
                                      List<ProductionSalesInventory> listProcess,
                                      List<ProductionSalesInventory> listFinished){
        Double totalRawMaterial = listRawMaterial.stream()
                .map(p -> p.getInventoryValueMb())
                .reduce(0.0,Double::sum);
        totalRawMaterial = Math.round(totalRawMaterial*100.0)/100.0;
        Double totalProcess = listProcess.stream()
                .map(p -> p.getInventoryValueMb())
                .reduce(0.0,Double::sum);
        totalProcess = Math.round(totalProcess*100.0)/100.0;
        Double totalFinishedInventory = listFinished.stream()
                .map(p -> p.getInventoryValueFinished())
                .reduce(0.0,Double::sum);
        totalFinishedInventory = Math.round(totalFinishedInventory*100.0)/100.0;
        Double totalFinishedFactor = listFinished.stream()
                .map(p -> p.getFactor())
                .reduce(0.0,Double::sum);
        totalFinishedFactor = Math.round(totalFinishedFactor*100.0)/100.0;
        List<Resume> resumeList = new ArrayList<>();

        resumeList.add(createResume("Total Valor inventario Materia Prima",totalRawMaterial));
        resumeList.add(createResume("Total Valor inventario Productos en Proceso",totalProcess));
        resumeList.add(createResume("Total Valor inventario Productos Terminados/Mercaderia (A)", totalFinishedInventory));
        resumeList.add(createResume("Total Inventarios",totalRawMaterial + totalProcess + totalFinishedInventory));
        resumeList.add(createResume("Total Factor Ponderado (B)", totalFinishedFactor ));
        Double mb = totalFinishedFactor/totalFinishedInventory;
        mb = Math.round(mb*10000.0)/10000.0;
        resumeList.add(createResume("MB Ponderados (B/A)", mb));

        return resumeList;
    }

    private Resume createResume(String detail, Double amount){
        Resume resume = new Resume();
        resume.setDetail(detail);
        resume.setAmount(amount);
        return resume;
    }
}
