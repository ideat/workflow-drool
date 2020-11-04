package com.mindware.workflow.core.service.data.legal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentSubmittedDto {
    private Integer firstYear;

    private String originalPhotocopyFirstYear;

    private Integer secondYear;

    private String originalPhotocopySecondYear;

    private Integer thirdYear;

    private String originalPhotocoyThirdYear;
}
