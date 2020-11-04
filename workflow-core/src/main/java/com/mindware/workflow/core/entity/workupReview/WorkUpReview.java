package com.mindware.workflow.core.entity.workupReview;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WorkUpReview {
    private UUID id;

    private Integer numberRequest;

    private String category;

    private String item;

    private String result;

    private LocalDate dateRegister;



}
