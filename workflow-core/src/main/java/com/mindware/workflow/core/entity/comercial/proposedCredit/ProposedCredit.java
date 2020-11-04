package com.mindware.workflow.core.entity.comercial.proposedCredit;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ProposedCredit {
    private UUID id;

    private UUID idClient;

    private Double amount;

    private String currency;

    private Double rateInterest;

    private Integer term;

    private String typeCredit;

    private String typeGuarantee;

    private String destinationLoan;

    private String typeOperation;

    private String caedec;

    private LocalDate registerDate;

}
