package com.mindware.workflow.core.service.data.paymentPlan.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentPlanDto {

    private String fullName;

    private Integer numberRequest;

    private Date requestDate;

    private Double amount;

    private Integer feeNumber;

    private String typeFee;

    private String currency;

    private Double rateInterest;

    private Integer paymentPeriod;

    private Integer quotaNumber;

    private Date paymentDate;

    private Double capital;

    private Double interest;

    private Double secureCharge;

    private Double otherCharge;

    private Double fee;

    private Double residue;

    private Double teac;

    private Double  tea;

    private Double itf;
}
