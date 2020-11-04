package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ExchangeRate {
    private UUID id;

    private String currency;

    private Double exchange;

    private LocalDate startValidity;

    private LocalDate endValidity;

    private String state;
}
