package com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantForStatementDto {
    private String typeApplicant;

    private String names;

    private String lastNames;

    private String marriedLastName;

    private String homeAddress;

    private String workAddress;

    private String city;

    private String block;

    private String province;

    private String typeHome;

    private String identify;

    private String nit;

    private String homePhone;

    private String workPhone;

    private String cellPhone;

    private Integer numberApplicantSpouse;

    private Integer numberApplicant;

    private String namesSpouse;

    private String lastNamesSpouse;

    private String nameCompanyWork;

    private String civilStatus;

    private Integer dependentNumber;

    private String workzone;

    public String getLastName(){
        if(this.marriedLastName==null || this.marriedLastName.equals("")){
            return this.lastNames;
        }else {
            return lastNames.concat(" de ").concat(this.marriedLastName);
        }
    }

}
