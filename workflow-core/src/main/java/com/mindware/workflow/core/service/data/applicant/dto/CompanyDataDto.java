package com.mindware.workflow.core.service.data.applicant.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CompanyDataDto {
    private String fullName;

    private String identify; //idCardComplete

    private String address;

    private String building;

    private String office;

    private String city;

    private String province;

    private String block;

    private String phone;

    private String email;

    private String comercialNumber;

    private String societyType;

    private String initials;

    private Integer antiquityArea;

    private Integer numberEmployees;

    private String webPage;

    private LocalDate constitutionDate;

    private String nameCompanyWork;

    private String nit;
}
