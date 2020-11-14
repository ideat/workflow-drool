package com.mindware.workflow.core.entity.creditRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySizeIndicator {
    private String typeCompany;

    private Double annualSale;

    private Double numberEmployees;

    private Double patrimony;

    private Double indicator;

    private String indicatorClassification;

    private String description;
}
