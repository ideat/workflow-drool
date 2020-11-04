package com.mindware.workflow.core.service.data.office.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class BranchOfficeDto implements Serializable {
    private UUID id;

    private int internalCode;

    private String name;

    private String address;

    private String phone;

    private String city;

    private String province;

    private String typeOffice;

    private String signatories;

}
