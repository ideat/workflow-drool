package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeDependent;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VaeDependentReportDto {
    private String fullName;
    private String nameCompanyWork;
    private Integer dependentNumber;
    private String profession;

    private List<SalaryAnalysisDto> salaryAnalysisDtoList;

    private List<PatrimonialStatement> expensesPatrimonialStatementList;
    private List<PatrimonialStatement> basicExpensesPatrimonialStatementList;

    private Double totalIncome; //total ingresos

    private Double dulyGuaranteed; //debidamente garantizado
    private Double dulyNoGuranteed;//No debidamente garantizado
    private Double noRentMaximumFee1;
    private Double noRentMaximumFee2;
    private Double noRentMaximumFee3;
    private Double rentMaximumFee1;
    private Double rentMaximumFee2;
    private Double rentMaximumFee3;
    private Double fee;
    private Double ratio;
    private Double availableAfterExpenses;
    private Double maxFeeNoDulyGuarantee;
    private Double maxFeeDulyGuarantee;
    private Double superAvit;
}
