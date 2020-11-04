package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductSalesBuys {
    private UUID id;

    private String typeRegister;

    private String product;

    private String unit;

    private Integer quantity;

    private Integer frecuency;

    private Double priceCost;

    private Double priceSale;

    private Double totalBuys;

    private Double totalSales;
}
