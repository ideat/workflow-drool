package com.mindware.workflow.core.service.data.creditResolution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GuaranteesResolution {
    private String description;
    private Double mortgage; //valor hipotecario;
    private Double commercialValue;
    private LocalDate appraisalDate; //fecha avaluo
    private Double guaranteeAmount;
    private String mortgageGrade; //grado hipoteca
    private String proficient;  //perito fieldtext15

}
