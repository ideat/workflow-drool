package com.mindware.workflow.core.service.data.exceptions.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorizationExceptionReportDto {
    private String firstName;
    private String lastName;
    private Double amount;
    private String currency;
    private String destination;
    private Double rateInterest;
    private Integer term;
    private String typeTerm;
    private String guaranteeDescription;
    private String exceptionDescription;
    private String justification;
    private LocalDate exceptionRegister;
    private String typeException;
    private String statusReview;

    public String getFullName(){
        return this.firstName +" "+ this.lastName;
    }

    public String getTermInMonth(){
        if(typeTerm.equals("ANUAL")){
           term = term*12;
        }else if(typeTerm.equals("DIA")){
            term = term/30;
        }

        return term.toString() + " MESES";
    }

    public String getTypeException(){
        if(typeException.equals("TEMPORAL")){
            return "FORMULARIO DE EXCEPCION TRANSITORIA";
        }else{
            return "FORMULACION DE EXCEPCION PERMANENTE";
        }
    }

}
