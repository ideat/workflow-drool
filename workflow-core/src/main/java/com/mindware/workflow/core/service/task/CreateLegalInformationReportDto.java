package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.legal.*;
import com.mindware.workflow.core.service.data.legal.dto.LegalInformationReportDto;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CreateLegalInformationReportDto {

    public static LegalInformationReportDto generate(LegalInformation legalInformation, CreditRequest creditRequest, Applicant applicant, Map<String,String> names) throws IOException {

        LegalInformationReportDto legalInformationReportDto = new LegalInformationReportDto();

//        LegalInformationCreditRequestDto legalInformationCreditRequestDto = new LegalInformationCreditRequestDto();

        legalInformationReportDto.setNameOfficer(names.get("nameOfficer"));
        legalInformationReportDto.setNameLegalAdviser(names.get("nameLegalAdviser"));
        legalInformationReportDto.setNameLegalAnalyst(names.get("legalAnalyst"));
        legalInformationReportDto.setNameApplicant(applicant.getFullName());
        legalInformationReportDto.setCity(applicant.getCity());
        legalInformationReportDto.setReportNumber(legalInformation.getReportNumber());
        legalInformationReportDto.setNumberRequest(legalInformation.getNumberRequest());
        legalInformationReportDto.setRegistration(legalInformation.getRegistration());
        legalInformationReportDto.setPropertyType(legalInformation.getPropertyType());
        legalInformationReportDto.setLocation(legalInformation.getLocation());
        legalInformationReportDto.setSurface(legalInformation.getSurface());
        legalInformationReportDto.setNorth(legalInformation.getNorth());
        legalInformationReportDto.setSouth(legalInformation.getSouth());
        legalInformationReportDto.setEast(legalInformation.getEast());
        legalInformationReportDto.setWest(legalInformation.getWest());

        if(legalInformation.getPublicWritingList()==null || legalInformation.getPublicWritingList().equals("[]") || legalInformation.getPublicWritingList().equals("")){
            List<PublicWritingList> pw = new ArrayList<>();
            legalInformationReportDto.setListPublicWritingList(pw);
        }else{
            legalInformationReportDto.setListPublicWritingList((List<PublicWritingList>) getObject(legalInformation.getPublicWritingList(),"publicWritingList"));
        }

        if(legalInformation.getOwners()==null || legalInformation.getOwners().equals("[]") || legalInformation.getOwners().equals("") ){
            List<Owners> l = new ArrayList<>();
            legalInformationReportDto.setListOwners(l);
        }else {
            legalInformationReportDto.setListOwners((List<Owners>) getObject(legalInformation.getOwners(), "owner"));
        }
        legalInformationReportDto.setTypeTitle(legalInformation.getTypeTitle());
        legalInformationReportDto.setPublicDeed(legalInformation.getPublicDeed());
        legalInformationReportDto.setDateDeed(legalInformation.getDateDeed());
        legalInformationReportDto.setGivenBy(legalInformation.getGivenBy());

        if(legalInformation.getRegistrationSeat()==null || legalInformation.getRegistrationSeat().equals("[]") || legalInformation.getRegistrationSeat().equals("")){
            List<Seat> seatList = new ArrayList<>();
            legalInformationReportDto.setListRegistrationSeat(seatList);
        }else {
            legalInformationReportDto.setListRegistrationSeat((List<Seat>) getObject(legalInformation.getRegistrationSeat(), "seat"));
        }
        legalInformationReportDto.setListDocumentSubmitted((List<DocumentSubmitted>) getObject(legalInformation.getDocumentsSubmitted(),"documentSubmitted"));

        if(legalInformation.getDataDocument()==null || legalInformation.getDataDocument().equals("[]") || legalInformation.getDataDocument().equals("")){
            List<DataDocument> dataDocuments = new ArrayList<>();
            legalInformationReportDto.setListDataDocument(dataDocuments);
        }else {
            legalInformationReportDto.setListDataDocument((List<DataDocument>) getObject(legalInformation.getDataDocument(), "dataDocument"));
        }
        List<GenericItem> genericItemList = new ArrayList<>();
        if(legalInformation.getDetails()==null || legalInformation.getDetails().equals("[]") || legalInformation.getDetails().equals("")) {

        }else{
            genericItemList = (List<GenericItem>) getObject(legalInformation.getDetails(), "generic");
        }
        legalInformationReportDto.setListObservations(genericItemList.stream().filter(value -> value.getTypeItem()
                .equals("Observacion")).collect(Collectors.toList()));
        legalInformationReportDto.setListMissingDocumentation(genericItemList.stream().filter(value -> value.getTypeItem()
                .equals("Documentacion Faltante")).collect(Collectors.toList()));
        legalInformationReportDto.setListContractRequirements(genericItemList.stream().filter(value -> value.getTypeItem()
                .equals("Requisito")).collect(Collectors.toList()));
        legalInformationReportDto.setListConclusion(genericItemList.stream().filter(value -> value.getTypeItem()
                .equals("Conclusion")).collect(Collectors.toList()));
        legalInformationReportDto.setListClarifications(genericItemList.stream().filter(value -> value.getTypeItem()
                .equals("Aclaracion")).collect(Collectors.toList()));
        legalInformationReportDto.setCreationDate(legalInformation.getCreationDate());



        return legalInformationReportDto;

    }

    private static List<? extends Object> getObject(String json, String type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> objectList = new ArrayList<>();
        if(type.equals("owner")){
            objectList = Arrays.asList(mapper.readValue(json, Owners[].class));

        }else if(type.equals("seat")){
            objectList = Arrays.asList(mapper.readValue(json, Seat[].class));
        }else if(type.equals("documentSubmitted")){
//            String aux = json.replaceAll("\\\\","");
//            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
            objectList = Arrays.asList(mapper.readValue(json, DocumentSubmitted[].class));
        }else if(type.equals("dataDocument")){
            objectList = Arrays.asList(mapper.readValue(json,DataDocument[].class));
        }else if(type.equals("generic")){
            objectList = Arrays.asList(mapper.readValue(json,GenericItem[].class));
        }
        return objectList;
    }
}
