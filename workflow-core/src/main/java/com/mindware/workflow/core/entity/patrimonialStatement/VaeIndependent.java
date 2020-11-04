package com.mindware.workflow.core.entity.patrimonialStatement;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VaeIndependent {
    private UUID id;

    private String salesProjection;

    private String productSales;

    private String productBuys;

    private String expenses;
}
