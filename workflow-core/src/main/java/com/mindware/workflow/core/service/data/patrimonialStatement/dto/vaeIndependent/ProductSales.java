package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSales {
    private String typeRegister;

    private String product;

    private String unit;

    private Integer quantity;

    private Integer frecuency;

    private Double priceCost;

    private Double priceSale;

    private Double totalBuys;

    private Double totalSales;

    private Double percentageTotalSales;

    private Double mub;

    private Double mubp;
}
