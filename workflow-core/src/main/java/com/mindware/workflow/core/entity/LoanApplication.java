package com.mindware.workflow.core.entity;

import com.mindware.workflow.core.enums.TypeTerm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class LoanApplication {

    private UUID id;

    private UUID idApplicant;

    @NotNull(message = "'Fecha de solicitud' no puede ser omitida")
    private LocalDate dateApplication;

    @Positive(message = "'Monto solicitado' debe ser positivo")
    private Float loanAmount;

    @NotNull(message = "'Moneda' no puede ser omitida")
    private String currency;

    @Positive(message = "'Tasa' tienes que ser positiva")
    @NotNull(message = "'Tasa' no puede ser omitida")
    private Float rate;

    private Float baseRate;

    private Float rateTre;

    private Integer waitingPeriod; //periodo de gracia intereses

    private Integer periodInitBaseRate;

    @Positive(message = "'Plazo' no puede ser negativo")
    @NotNull(message = "'Plazo' no puede ser omitido")
    private Integer term;

    @NotNull(message = "'Tipo de plazo' no puede ser omitido")
    private TypeTerm typeTerm;

    @NotNull(message = "'CAEDEC destino' no puede ser omitido")
    private String caedec;

    private String financialUse;

    @NotNull(message = "'Tipo de cuota' no puede ser omitido")
    private String typeFee;

    @NotNull(message = "'Dia de pago' no puede ser omitido")
    private Integer dayPayment;

    @NotNull(message = "'Frecuencia de pago' no puede ser omitida")
    private String paymentFrecuency;

    private String charge; //json con los seguros desgravamen, incencios, etc

    private String creditDestination;

    private String savingBoxNumber;

    private String creditZone;

    private boolean reprograming;

    private String state;

    private LocalDate disbursementDate; //fecha desembolso

}
