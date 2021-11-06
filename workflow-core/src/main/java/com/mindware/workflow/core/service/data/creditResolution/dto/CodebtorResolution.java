package com.mindware.workflow.core.service.data.creditResolution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CodebtorResolution {

    private String fullNameCodebtor;

    private String idCardCompleteCodebtor;

    private String fullNameSpouse;

    private String idCardCompleteSpouse;

    private String homeAddress;

    private String phones;

    private String caedecCodebtor;

    private Double patrimony;

    private LocalDate customerFrom;

    private Double reciprocity;
}
