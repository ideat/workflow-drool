package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummarySalesDto {
    private int sequence;

    private String month;

    private Double total;
}
