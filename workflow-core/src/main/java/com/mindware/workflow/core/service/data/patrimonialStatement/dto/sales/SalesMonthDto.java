package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SalesMonthDto {
    private LocalDate date;

    private String activity;

    private Double amount;

}
