package com.mindware.workflow.core.entity.kiosco;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductKiosco {
    private UUID id;

    private String concept;

    private String product;

    private Double minAmount;

    private Double maxAmount;

    private Double secure;

    private Double allRisk;

    private Integer term;

    private Double rate;

    private String guarantee;

    private String conditions;

    private String state;

}
