package com.mindware.workflow.core.service.data.stadistic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StagesPercentageDto {
    private String category;
    private String label;
    private Double percentage;

}
