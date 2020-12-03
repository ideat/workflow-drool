package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement;

import com.mindware.workflow.core.entity.CompanyData;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.applicant.dto.CompanyDataDto;
import com.mindware.workflow.core.service.data.creditResolution.dto.UnsecuredGuarantee;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.StatementApplicants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatrimonialStatementApplicantDto {
    private Integer numberRequest;

    private String currency;

    private String currencyName;

    private Double amount;

    private String literalAmount;

    private String typeCredit;

    private String nameTypeCredit;

    private Integer term;

    private String typeTerm;

    private Integer paymentPeriod;

    private String typeFee;

    private Double rateInterest;

    private LocalDate requestDate;

    private Double totalAssets;

    private Double totalLiabilities;

    private Double patrimony;

    private Double totalEarning;

    private Double totalExpense;

    private Double balance;

    private String destination;

    private String fullIdCard;

    private Integer dependentNumber;

    private String nameCompanyWork;

    private List<CompanyDataDto> companyDataList;

    private List<StatementApplicants> guaranteeList;

    private List<ApplicantForStatementDto> applicantDebtorList;

    private List<ApplicantForStatementDto> applicantCoDebtorList;

    private List<ApplicantForStatementDto> applicantGuarantorList;

    private List<PatrimonialStatement> assetBankCheckingAccountList; //cuenta corriente

    private List<PatrimonialStatement> assetReceivableList; // documento x cobrar

    private List<PatrimonialStatement> assetPropertyList; // inmueble

    private List<PatrimonialStatement> assetVehicleList; // Vehiculo

    private List<PatrimonialStatement> machineryList;// maquinaria equipo

    private List<PatrimonialStatement> cashList;// Efectivo

    private List<PatrimonialStatement> inventoryList;//inventario

    private List<PatrimonialStatement> assetOtherAssetsList; // Otros activos

    private List<PatrimonialStatement> liabilityDocumentsToPayList; //Pasivo documentos y cuentas x pagar

    private List<PatrimonialStatement> liabilityCreditCardsList; //Pasivos Tarjetas de Credito

    private List<PatrimonialStatement> liabilityOtherLiabilitiesList; //Pasivos Otros pasivos

    private List<PatrimonialStatement> earningList;

    private List<PatrimonialStatement> expenseList;

    private List<UnsecuredGuarantee> unsecuredGuarantee;

}
