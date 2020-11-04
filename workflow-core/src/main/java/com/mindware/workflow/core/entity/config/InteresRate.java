package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class InteresRate {

    private UUID id;

    @NotNull(message = "'Tasa' no puede ser omitida")
    @Positive(message = "'Tasa' tiene que tener un valor positivo")
    private Float rate;

    private LocalDate efectiveDate;

    private String currency;

    private String state;

    @PositiveOrZero(message = "'Monto minimo' no puede ser negativo")
    private Float minAmount;

    @PositiveOrZero(message = "'Monto maximo' no puede ser negativo")
    private Float maxAmount;
}
