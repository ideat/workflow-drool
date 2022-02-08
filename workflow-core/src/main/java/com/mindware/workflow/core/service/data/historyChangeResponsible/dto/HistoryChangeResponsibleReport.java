package com.mindware.workflow.core.service.data.historyChangeResponsible.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class HistoryChangeResponsibleReport {

    private Integer numberRequest;

    private LocalDate dateChange;

    private String rolName;

    private String oldResponsibleName;

    private String newResponsibleName;

    private String reasonChangeResponsible;

    private String oldOfficeName;

    private String newOfficeName;

    private String processedBy;

    private String firstName;

    private String secondName;

    private String lastName;

    private String motherLastName;

    private String marriedLastName;

    public String getFullName(){

        String mal = Optional.ofNullable(this.marriedLastName).orElse("");
        return Optional.ofNullable(this.firstName).orElse("")+" "
                +Optional.ofNullable(this.secondName).orElse("") + " "
                +Optional.ofNullable(this.lastName).orElse("")+ " "
                +Optional.ofNullable(this.motherLastName).orElse("")+ " "
                + (!mal.equals("")?"de " + mal:"");

    }
}
