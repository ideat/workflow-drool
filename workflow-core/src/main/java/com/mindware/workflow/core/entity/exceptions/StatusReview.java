package com.mindware.workflow.core.entity.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatusReview {
    private String loginUser;
    private String state;
    private LocalDate registerDate;
}
