package com.mindware.workflow.core.service.data.observation.dto;

import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ObservationCreditRequestApplicant {
    private Integer numberRequest;

    private Integer numberApplicant;

    private String nameOfficial;

    private String firstName;

    private String lastName;

    private String fullNameClient;

    private String typeCredit;

    private String typeGuarantee;

    private String mainActivity;

    private String agency;

    private Integer numberCredit;

    private Double rateInterest;

    private Double amount;

    private String currency;

    private String destination;

    private String state;

    private Double indicator1; //Relacion cuota/ingreso efv

    private Double indicator2; //Relacion cuota + otras / ingreso

    private Double indicator3; //Relacion patrimonio/credito

    private Double indicator4; //Grantia/deuda

    private List<TemplateObservation> observations;

}
