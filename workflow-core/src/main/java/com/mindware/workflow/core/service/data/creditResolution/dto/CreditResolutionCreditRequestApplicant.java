package com.mindware.workflow.core.service.data.creditResolution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreditResolutionCreditRequestApplicant {

    private LocalDate creditResolutionDate;

    private Integer creditNumber;

    private String fullNameApplicant;

    private String idCardCompleteApplicant;

    private String fullNameSpouse;

    private String idCardCompleteSpouse;

    private String homeAddress;

    private String phones;

    private String caedecApplicant;
//
    private String caedecCreditRequest;

    private String typeCredit;

    private String typeCreditDescription;

    private String objectCredit;

    private Double secureOverAllRisk;

    private String creditDestination;
 //
    private String applicantRaiting;

    private Double patrimony;

    private LocalDate customerFrom;

    private Double reciprocity;

    private String modality;

    private Double amount;

    private String currency;

    private String amortization;

    private String rate;

    private String term;

    private Double teac;

    private Double sureRelief; //seguro desgravamen

    private String sector;

    private String item;

    private String creditObject;

    private List<GuaranteesResolution> guaranteesResolutionList;

    private List<GuarantorResolution> guarantorResolutionList;

    private List<DirectIndirectDebts> directIndirectsDebtsList;

    private List<Indicators> indicatorsList;

    private List<Exceptions> exceptionsList;

    private List<Disbursements> disbursementsList;

    private String typeResolution;

    private String markRegional;

    private String markLevel2;

    private String markLevel3;

    private String markLevel1;

    private String creditRequestRelevantInformation;

    private String nameOfficer;

    private Integer numberCreditRequest;

    private String nameOffice;

    private List<UnsecuredGuarantee> unsecuredGuarantee;
}
