package com.mindware.workflow.core.service.data.legal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mindware.workflow.core.entity.legal.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LegalInformationReportDto {

    private String nameOfficer;

    private String nameLegalAdviser; //asesor juridico

    private String nameLegalAnalyst; //analista legal

    private String nameApplicant;

    private String city;

    private String reportNumber;

    private Integer numberRequest;

    private String registration; //matricula

    private List<Seat> listRegistrationSeat; //json

    private String propertyType; //tipo inmueble

    private String location;

    private Double surface; //superficie

    private String north;

    private String south;

    private String east;

    private String west;

    private List<Owners> listOwners;//json

    private String typeTitle; //tipo

    private String publicDeed;;//escritura publica

    private LocalDate dateDeed; //fecha escritura

    private String givenBy; //otorgado por

    private List<DocumentSubmitted> listDocumentSubmitted;

    private List<DataDocument> listDataDocument;

    private List<GenericItem> listObservations;

    private List<GenericItem> listMissingDocumentation;

    private List<GenericItem> listContractRequirements;

    private List<GenericItem> listConclusion;

    private List<GenericItem> listClarifications;

    private LocalDate creationDate;

}
