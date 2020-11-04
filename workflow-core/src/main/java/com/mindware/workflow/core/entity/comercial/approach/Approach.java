package com.mindware.workflow.core.entity.comercial.approach;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Approach {
    private UUID id;

    private UUID idProposedCredit;

    private String state;

    private LocalDate initDate;

    private LocalDate endDate;

    private Integer month;

    private Integer week;

    private LocalDate followUpDate;

    private String observation;
}
