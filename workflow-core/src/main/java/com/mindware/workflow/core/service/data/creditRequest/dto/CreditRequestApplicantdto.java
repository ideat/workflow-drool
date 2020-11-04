package com.mindware.workflow.core.service.data.creditRequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class CreditRequestApplicantdto {
    private int numberRequest;
    private Double amount;
    private String currency;
    private LocalDate requestDate;
    private String state;
    private String firstName;
    private String lastName;
    private String homeaddress;
    private String idCard;
    private String idCardExpedition;
    private String civilStatus;
    private String workaddress;
    private String profession;
    private String city;
    private String province;
    private String block;
    private String typeHome;

    private UUID idApplicant;
    private UUID idCreditRequest;
    private UUID id;
    private String loginUser;
    private int numberApplicant;
    private String typeRelation;
    private Integer numberApplicantSpouse;
    private String firstNameSpouse;
    private String lastNameSpouse;
    private String homeaddressSpouse;
    private String idCardSpouse;
    private String idCardExpeditionSpouse;
    private String civilStatusSpouse;
    private String workaddressSpouse;
    private String professionSpouse;
    private String citySpouse;
    private String provinceSpouse;
    private String blockSpouse;
    private String typeHomeSpouse;
    private String cityOffice;
    private Integer codeOffice;
    private String nameOffice;
    private String typeCredit;
    private String objectCredit;

    public String getFullName(){
        return firstName + " " + lastName;
    }
    public String getFullNameSpouse(){
        return firstNameSpouse + " " + lastNameSpouse;
    }
    public String getFullIdCard(){
        return idCard+idCardExpedition;
    }

    public String getFullIdCardSpouse(){
        return idCardSpouse+idCardExpeditionSpouse;
    }
}
