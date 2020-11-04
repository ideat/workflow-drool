package com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OperativeExpenses {
    private UUID id;

    private String expense;

    private Double amount;
}
