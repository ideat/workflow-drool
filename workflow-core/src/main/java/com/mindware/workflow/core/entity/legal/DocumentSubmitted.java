package com.mindware.workflow.core.entity.legal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DocumentSubmitted {

    private Integer firstYear; //gestio

    private String originalPhotocopyFirstYear;

    private Integer secondYear;

    private String originalPhotocopySecondYear;

    private Integer thirdYear;

    private String originalPhotocopyThirdYear;

    private Integer fourthYear;

    private String originalPhotocopyFourthYear;

    private Integer fiftyYear;

    private String originalPhotocopyFiftyYear;
}
