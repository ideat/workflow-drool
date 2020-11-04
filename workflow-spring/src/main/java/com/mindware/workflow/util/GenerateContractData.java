package com.mindware.workflow.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.config.Signatories;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.service.data.contract.dto.ContractData;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import com.mindware.workflow.core.service.task.NumberToLiteral;
import com.mindware.workflow.core.service.task.UtilPaymentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerateContractData {

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;

    @Autowired
    RepositoryOffice repositoryOffice;

    @Autowired
    RepositoryParameter repositoryParameter;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryPaymentPlanDto repositoryPaymentPlanDto;

    @Autowired
    RepositoryTypeCredit repositoryTypeCredit;


    public  ContractData getContractData(Integer numberRequest) throws IOException {

        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        List<CreditRequestApplicant> creditRequestApplicantList = repositoryCreditRequestApplicant.getByNumberRequest(numberRequest);
        List<Applicant> applicantList = new ArrayList<>();
        for(CreditRequestApplicant ca : creditRequestApplicantList){
            Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(ca.getNumberApplicant()).get();
            if(applicant.getNumberApplicantSpouse()!=null && applicant.getNumberApplicantSpouse()>0 ){
                Applicant applicantSpouse = repositoryApplicant.getApplicantByNumberApplicant(applicant.getNumberApplicantSpouse()).get();
                applicantList.add(applicantSpouse);
            }
            applicantList.add(applicant);
        }

        Office office = repositoryOffice.getOfficeByInternalCode(creditRequest.getIdOffice()).get();
        List<Parameter> parameterList = repositoryParameter.getAllParameters();


        ContractData contractData = new ContractData();
        int countCodebtor=0;
        int countGuarantor=0;
        for(CreditRequestApplicant ca:creditRequestApplicantList){
            Applicant applicant = applicantList.stream()
                    .filter(a -> a.getNumberApplicant().equals(ca.getNumberApplicant())).collect(Collectors.toList()).get(0);
            if(ca.getTypeRelation().equals("deudor")) {
                contractData.setSavingBox(applicant.getSavingAccount());
                contractData.setFullNameApplicant(applicant.getFullName());
                contractData.setIdCardApplicant(applicant.getFullIdCard());
                contractData.setAddressHomeApplicant(applicant.getHomeaddress());
                contractData.setCivilStatusDebtor(applicant.getCivilStatus());
                contractData.setCityDebtor(applicant.getCity());
                contractData.setZoneDebtor(applicant.getZone());
                contractData.setHomeAddressReferenceDebtor(applicant.getHomeAddressReference());
                contractData.setBlockDebtor(applicant.getBlock());
                if(applicant.getNumberApplicantSpouse()>0){
                    Applicant applicantSpouse = applicantList.stream()
                            .filter(a -> a.getNumberApplicant()
                                    .equals(applicant.getNumberApplicantSpouse()))
                            .collect(Collectors.toList()).get(0);
                    contractData.setFullNameSpouseApplicant(applicantSpouse.getFullName());
                    contractData.setIdCardSpouseApplicant(applicantSpouse.getFullIdCard());
                    contractData.setAddressHomeSpouseApplicant(applicantSpouse.getHomeaddress());
                    contractData.setCivilStatusSpouse(applicantSpouse.getCivilStatus());

                    contractData.setCitySpouse(applicantSpouse.getCity());
                    contractData.setZoneSpouseDebtor(applicantSpouse.getZone());
                    contractData.setHomeAddressReferenceSpouse(applicantSpouse.getHomeAddressReference());
                    contractData.setBlockSpouse(applicantSpouse.getBlock());
                }

            }else if(ca.getTypeRelation().equals("codeudor")){
                countCodebtor++;
                if(countCodebtor==1){
                    contractData.setFullNameCodebtor1(applicant.getFullName());
                    contractData.setIdCardCodebtor1(applicant.getFullIdCard());
                    contractData.setAddressHomeCodebtor1(applicant.getHomeaddress());
                    contractData.setCivilStatusCodebtor1(applicant.getCivilStatus());
                    contractData.setCityCodebtor1(applicant.getCity());
                    contractData.setZoneCodebtor1(applicant.getZone());
                    contractData.setHomeAddressReferenceCodebtor1(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseCodebtor1(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseCodebtor1(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseCodebtor1(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseCodebtor1(applicantSpouse.getHomeaddress());

                        contractData.setCivilStatusSpouseCodebtor1(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseCodebtor1(applicantSpouse.getCity());
                        contractData.setZoneSpouseCodebtor1(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseCodebtor1(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseCodebtor1(applicantSpouse.getBlock());
                    }
                }else if(countCodebtor==2){
                    contractData.setFullNameCodebtor2(applicant.getFullName());
                    contractData.setIdCardCodebtor2(applicant.getFullIdCard());
                    contractData.setAddressHomeCodebtor2(applicant.getHomeaddress());
                    contractData.setCivilStatusCodebtor2(applicant.getCivilStatus());
                    contractData.setCityCodebtor2(applicant.getCity());
                    contractData.setZoneCodebtor2(applicant.getZone());
                    contractData.setHomeAddressReferenceCodebtor2(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseCodebtor2(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseCodebtor2(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseCodebtor2(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseCodebtor2(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseCodebtor2(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseCodebtor2(applicantSpouse.getCity());
                        contractData.setZoneSpouseCodebtor2(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseCodebtor2(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseCodebtor2(applicantSpouse.getBlock());
                    }
                }else if(countCodebtor==3){
                    contractData.setFullNameCodebtor3(applicant.getFullName());
                    contractData.setIdCardCodebtor3(applicant.getFullIdCard());
                    contractData.setAddressHomeCodebtor3(applicant.getHomeaddress());
                    contractData.setCivilStatusCodebtor3(applicant.getCivilStatus());
                    contractData.setCityCodebtor3(applicant.getCity());
                    contractData.setZoneCodebtor3(applicant.getZone());
                    contractData.setHomeAddressReferenceCodebtor3(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseCodebtor3(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseCodebtor3(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseCodebtor3(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseCodebtor3(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseCodebtor3(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseCodebtor3(applicantSpouse.getCity());
                        contractData.setZoneSpouseCodebtor3(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseCodebtor3(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseCodebtor3(applicantSpouse.getBlock());
                    }
                }else if(countCodebtor==4){
                    contractData.setFullNameCodebtor4(applicant.getFullName());
                    contractData.setIdCardCodebtor4(applicant.getFullIdCard());
                    contractData.setAddressHomeCodebtor4(applicant.getHomeaddress());
                    contractData.setCivilStatusCodebtor4(applicant.getCivilStatus());
                    contractData.setCityCodebtor4(applicant.getCity());
                    contractData.setZoneCodebtor4(applicant.getZone());
                    contractData.setHomeAddressReferenceCodebtor4(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseCodebtor4(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseCodebtor4(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseCodebtor4(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseCodebtor4(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseCodebtor4(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseCodebtor4(applicantSpouse.getCity());
                        contractData.setZoneSpouseCodebtor4(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseCodebtor4(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseCodebtor4(applicantSpouse.getBlock());
                    }
                }
            }else if(ca.getTypeRelation().equals("garante")){
                countGuarantor++;
                if(countGuarantor==1){
                    contractData.setFullNameGuarantor1(applicant.getFullName());
                    contractData.setIdCardGuarantor1(applicant.getFullIdCard());
                    contractData.setAddressHomeGuarantor1(applicant.getHomeaddress());
                    contractData.setCivilStatusGuarantor1(applicant.getCivilStatus());
                    contractData.setCityGuarantor1(applicant.getCity());
                    contractData.setZoneGuarantor1(applicant.getZone());
                    contractData.setHomeAddressReferenceGuarantor1(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseGuarantor1(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseGuarantor1(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseGuarantor1(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseGuarantor1(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseGuarantor1(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseGuarantor1(applicantSpouse.getCity());
                        contractData.setZoneSpouseGuarantor1(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseGuarantor1(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseGuarantor1(applicantSpouse.getBlock());
                    }
                }else  if(countGuarantor==2){
                    contractData.setFullNameGuarantor2(applicant.getFullName());
                    contractData.setIdCardGuarantor2(applicant.getFullIdCard());
                    contractData.setAddressHomeGuarantor2(applicant.getHomeaddress());
                    contractData.setCivilStatusGuarantor2(applicant.getCivilStatus());
                    contractData.setCityGuarantor2(applicant.getCity());
                    contractData.setZoneGuarantor2(applicant.getZone());
                    contractData.setHomeAddressReferenceGuarantor2(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseGuarantor2(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseGuarantor2(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseGuarantor2(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseGuarantor2(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseGuarantor2(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseGuarantor2(applicantSpouse.getCity());
                        contractData.setZoneSpouseGuarantor2(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseGuarantor2(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseGuarantor2(applicantSpouse.getBlock());
                    }
                }else  if(countGuarantor==3){
                    contractData.setFullNameGuarantor3(applicant.getFullName());
                    contractData.setIdCardGuarantor3(applicant.getFullIdCard());
                    contractData.setAddressHomeGuarantor3(applicant.getHomeaddress());
                    contractData.setCivilStatusGuarantor3(applicant.getCivilStatus());
                    contractData.setCityGuarantor3(applicant.getCity());
                    contractData.setZoneGuarantor3(applicant.getZone());
                    contractData.setHomeAddressReferenceGuarantor3(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseGuarantor3(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseGuarantor3(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseGuarantor3(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseGuarantor3(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseGuarantor3(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseGuarantor3(applicantSpouse.getCity());
                        contractData.setZoneSpouseGuarantor3(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseGuarantor3(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseGuarantor3(applicantSpouse.getBlock());
                    }
                }else  if(countGuarantor==4){
                    contractData.setFullNameGuarantor4(applicant.getFullName());
                    contractData.setIdCardGuarantor4(applicant.getFullIdCard());
                    contractData.setAddressHomeGuarantor4(applicant.getHomeaddress());
                    contractData.setCivilStatusGuarantor4(applicant.getCivilStatus());
                    contractData.setCityGuarantor4(applicant.getCity());
                    contractData.setZoneGuarantor4(applicant.getZone());
                    contractData.setHomeAddressReferenceGuarantor4(applicant.getHomeAddressReference());
                    contractData.setBlockSpouseGuarantor4(applicant.getBlock());
                    if(applicant.getNumberApplicantSpouse()>0){
                        Applicant applicantSpouse = applicantList.stream()
                                .filter(a -> a.getNumberApplicant()
                                        .equals(applicant.getNumberApplicantSpouse()))
                                .collect(Collectors.toList()).get(0);
                        contractData.setFullNameSpouseGuarantor4(applicantSpouse.getFullName());
                        contractData.setIdCardSpouseGuarantor4(applicantSpouse.getFullIdCard());
                        contractData.setAddressHomeSpouseGuarantor4(applicantSpouse.getHomeaddress());
                        contractData.setCivilStatusSpouseGuarantor4(applicantSpouse.getCivilStatus());
                        contractData.setCitySpouseGuarantor4(applicantSpouse.getCity());
                        contractData.setZoneSpouseGuarantor4(applicantSpouse.getZone());
                        contractData.setHomeAddressReferenceSpouseGuarantor4(applicantSpouse.getHomeAddressReference());
                        contractData.setBlockSpouseGuarantor4(applicantSpouse.getBlock());
                    }
                }
            }
        }

        String typeTerm = creditRequest.getTypeTerm().equals("MES")?"Meses"
                :creditRequest.getTypeTerm().equals("DIA")?"Dias":"Años";
        NumberToLiteral numberToLiteral = new NumberToLiteral();
        contractData.setCurrency(creditRequest.getCurrency());
        Parameter parameter = parameterList.stream()
                .filter(p -> p.getCategory().equals("MONEDA") && p.getValue().equals(creditRequest.getCurrency()))
                .collect(Collectors.toList()).get(0);
        contractData.setNameCurrency(parameter.getDescription());
        contractData.setAmount(String.format("%,.2f",creditRequest.getAmount()));
        contractData.setLiteralAmount(numberToLiteral.Convert(creditRequest.getAmount()
                .toString(),true,"","float"));
        contractData.setRate(creditRequest.getRateInterest().toString());
        contractData.setBaseRateInteres(creditRequest.getBaseInterestRate().toString());
        contractData.setInitPeriodBaseRate(creditRequest.getInitPeriodBaseRate().toString());
        contractData.setTerm(String.valueOf(creditRequest.getTerm()));
        contractData.setLiteralTerm(numberToLiteral
                .Convert(String.valueOf(creditRequest.getTerm()),true,"",""));
        contractData.setTypeTerm(typeTerm);
        contractData.setTypeFee(creditRequest.getTypeFee());
        contractData.setDestination(creditRequest.getDestination());

        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);
        List<PaymentPlanDto> paymentPlantDtoList = repositoryPaymentPlanDto.getDataReportPaymentPlant(numberRequest);

        double[] payments = UtilPaymentPlan.getListFee(paymentPlanList,paymentPlantDtoList.get(0).getAmount());
        Double teac = UtilPaymentPlan.irr(payments, 0.01d) * 11.8356;

        contractData.setTeac(String.format("%,.2f", teac*100));
        contractData.setFixedDay(String.valueOf(creditRequest.getFixedDay()));
        contractData.setRequestDate(creditRequest.getRequestDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

//        parameter = parameterList.stream()
//                .filter(p -> p.getCategory().equals("TIPO CREDITO") && p.getValue().equals(creditRequest.getTypeCredit()))
//                .collect(Collectors.toList()).get(0);
        TypeCredit typeCredit = repositoryTypeCredit.getByExternalCode(creditRequest.getTypeCredit()).get();
        contractData.setTypeCredit(typeCredit.getDescription());

        String paymentFrecuency ="";
        String paymentFrecuencySingular="";
        if(creditRequest.getPaymentPeriod()==30){
            paymentFrecuency = "MENSUALES";
            paymentFrecuencySingular = "MENSUAL";
        }else if(creditRequest.getPaymentPeriod()==60){
            paymentFrecuency = "BIMESTRALES";
            paymentFrecuencySingular = "BIMESTRAL";
        }else if(creditRequest.getPaymentPeriod()==90){
            paymentFrecuency= "TRIMESTRALES";
            paymentFrecuencySingular = "TRIMESTRAL";
        }else if(creditRequest.getPaymentPeriod()==120){
            paymentFrecuency = "CUATRIMESTRALES";
            paymentFrecuencySingular = "CUATRIMESTRAL";
        }else if(creditRequest.getPaymentPeriod()==180){
            paymentFrecuency = "SEMESTRALES";
            paymentFrecuencySingular ="SEMESTRAL";
        }else if(creditRequest.getPaymentPeriod()==360){
            paymentFrecuency = "ANUALES";
            paymentFrecuencySingular = "ANUAL";
        }
        contractData.setPaymentFrequency(paymentFrecuency);
        contractData.setPaymentFrecuencySingular(paymentFrecuencySingular);

        Double totalPayment = Arrays.stream(payments).sum();
        contractData.setTotalPayment(String.format("%,.2f",totalPayment));
        contractData.setLiteralTotalPayment(numberToLiteral.Convert(totalPayment.toString(),true,"","float"));
        contractData.setLiteralBaseRateInteres(numberToLiteral.Convert(creditRequest.getBaseInterestRate().toString(),true,"","float"));
        contractData.setLiteralInitPeriodBaseRate(numberToLiteral.Convert(creditRequest.getInitPeriodBaseRate().toString(),true,"",""));

        ObjectMapper mapper =new ObjectMapper();
        List<Signatories> signatoriesList = Arrays.asList( mapper.readValue(office.getSignatorie(),new TypeReference<Signatories[]>(){}));
        signatoriesList = signatoriesList.stream()
                .filter(v -> v.getState().equals("ACTIVO")).collect(Collectors.toList());
        signatoriesList.sort(Comparator.comparing(Signatories::getPriority));
        int countSignatories=0;
        for(Signatories s:signatoriesList){
            countSignatories++;
            if(countSignatories==1){
                contractData.setNameLegalRepresentative1(s.getName());
                contractData.setIdCardLegalRepresentative1(s.getIdentifyCard());
                contractData.setPositionLegalRepresentative1(s.getPosition());
                contractData.setNumberNotaryLegalRepresentative1(s.getNumNotary());
                contractData.setNameNotaryLegalRepresentative1(s.getNameNotary());
                contractData.setJudicialDistrictLegalRepresentative1(s.getJudicialDistrict());
                contractData.setNumTestimonyLegalRepresentative1(s.getNumTestimony());
                contractData.setDateTestimonyLegalRepresentative1(s.getDateTestimony());
            }else  if(countSignatories==2){
                contractData.setNameLegalRepresentative2(s.getName());
                contractData.setIdCardLegalRepresentative2(s.getIdentifyCard());
                contractData.setPositionLegalRepresentative2(s.getPosition());
                contractData.setNumberNotaryLegalRepresentative2(s.getNumNotary());
                contractData.setNameNotaryLegalRepresentative2(s.getNameNotary());
                contractData.setJudicialDistrictLegalRepresentative2(s.getJudicialDistrict());
                contractData.setNumTestimonyLegalRepresentative2(s.getNumTestimony());
                contractData.setDateTestimonyLegalRepresentative2(s.getDateTestimony());
            }else  if(countSignatories==3){
                contractData.setNameLegalRepresentative3(s.getName());
                contractData.setIdCardLegalRepresentative3(s.getIdentifyCard());
                contractData.setPositionLegalRepresentative3(s.getPosition());
                contractData.setNumberNotaryLegalRepresentative3(s.getNumNotary());
                contractData.setNameNotaryLegalRepresentative3(s.getNameNotary());
                contractData.setJudicialDistrictLegalRepresentative3(s.getJudicialDistrict());
                contractData.setNumTestimonyLegalRepresentative3(s.getNumTestimony());
                contractData.setDateTestimonyLegalRepresentative3(s.getDateTestimony());
            }
        }


        return contractData;
    }
}
