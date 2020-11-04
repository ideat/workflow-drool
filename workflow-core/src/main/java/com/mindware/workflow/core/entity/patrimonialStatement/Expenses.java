package com.mindware.workflow.core.entity.patrimonialStatement;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Expenses {
    private UUID id;

    private String expense;

    private Double amount;
}
