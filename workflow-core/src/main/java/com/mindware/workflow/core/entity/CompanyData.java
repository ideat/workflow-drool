package com.mindware.workflow.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CompanyData {
    private String address;

    private String building;

    private String office;

    private String city;

    private String province;

    private String block;

    private String phones;

    private String email;

    private String comercialNumber;

    private String societyType;

    private String initials;

    private Integer antiquityArea;

    private Integer numberEmployees;

    private String webpage;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate constitutionDate;

}
