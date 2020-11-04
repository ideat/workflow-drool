package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeDependent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryAnalysisDto {
    private String typeClient;
    private Double salary1;
    private Double lawDiscounts1;
    private Double otherDiscounts1;
    private Double otherIncomes1;
    private Double availableSalary1;
    private String month2;
    private Double salary2;
    private Double lawDiscounts2;
    private Double otherDiscounts2;
    private Double otherIncomes2;
    private Double availableSalary2;
    private String month3;
    private Double salary3;
    private Double lawDiscounts3;
    private Double otherDiscounts3;
    private Double otherIncomes3;
    private Double availableSalary3;
    private Double mountlyAverage;
}
