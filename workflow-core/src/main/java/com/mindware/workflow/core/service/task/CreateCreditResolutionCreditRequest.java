package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.config.ObjectCredit;
import com.mindware.workflow.core.entity.config.ProductTypeCredit;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.Charge;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.dto.DirectIndirectDebts;
import com.mindware.workflow.core.service.data.creditResolution.dto.CreditResolutionCreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditResolution.dto.Disbursements;
import com.mindware.workflow.core.service.data.creditResolution.dto.Exceptions;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.DAYS;

public class CreateCreditResolutionCreditRequest {

    public static CreditResolutionCreditRequestApplicant generate(Applicant applicant, Optional<Applicant> spouse
            , CreditResolution creditResolution, CreditRequest creditRequest, TypeCredit typeCredit) throws IOException {
        CreditResolutionCreditRequestApplicant dataResolution = new CreditResolutionCreditRequestApplicant();
        ObjectMapper mapper = new ObjectMapper();
        dataResolution.setFullNameApplicant(applicant.getFullName());
        dataResolution.setIdCardCompleteApplicant(applicant.getFullIdCard());
        if (spouse.isPresent()) {
            dataResolution.setFullNameSpouse(spouse.get().getFullName());
            dataResolution.setIdCardCompleteSpouse(spouse.get().getFullIdCard());
        }else {
            dataResolution.setFullNameSpouse("");
            dataResolution.setIdCardCompleteSpouse("");
        }
        dataResolution.setCreditNumber(creditRequest.getNumberCredit());
        dataResolution.setCreditResolutionDate(creditResolution.getCreationDate());
        dataResolution.setHomeAddress(applicant.getHomeaddress());
        dataResolution.setPhones(applicant.getCellphone() + " " +applicant.getHomephone());
        dataResolution.setCaedecApplicant(applicant.getCaedec());
        dataResolution.setApplicantRaiting(creditResolution.getApplicantRating());
        dataResolution.setPatrimony(0.0);
        dataResolution.setCustomerFrom(applicant.getCustomerFrom());
        dataResolution.setReciprocity(creditResolution.getReciprocity());
        dataResolution.setCreditDestination(creditRequest.getDestination());
        dataResolution.setTypeCredit(creditRequest.getTypeCredit());
        dataResolution.setTypeCreditDescription(typeCredit.getDescription());
        dataResolution.setCaedecCreditRequest(creditRequest.getCaedec());
        dataResolution.setTypeResolution(creditResolution.getTypeResolution());
        dataResolution.setCreditRequestRelevantInformation(creditResolution.getCreditRequestRelevantInformation());
        String modality="";
        int factor = creditRequest.getPaymentPeriod()/30;

        if(creditRequest.getTypeFee().equals("PLAZO FIJO")){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            modality = "Pago a plazo fijo al vencimiento del DPF (" + creditRequest.getPaymentPlanEndDate().format(formatters) + ")" ;
        }else {
            modality = "Prestamos amortizable por " + creditRequest.getCurrency() + "-" + String.format("%,.2f", creditRequest.getAmount());
        }
        if(creditRequest.getGracePeriod()>0 && !creditRequest.getTypeGracePeriod().equals("NINGUNO")) {
            String gracePeriod = ", con period de gracia de " + creditRequest.getGracePeriod() + " meses a " + creditRequest.getTypeGracePeriod();
            modality = modality.concat(gracePeriod);
        }
        dataResolution.setModality(modality);//pendiente, ver si se puede mejorar

        dataResolution.setAmount(creditRequest.getAmount());
        dataResolution.setCurrency(creditRequest.getCurrency());

        String typeAmortization="";
        if(creditRequest.getTypeFee().equals("PLAZO FIJO")){
            typeAmortization = "Unico pago al vencimiento";
        }else {
            typeAmortization = "Cada " + creditRequest.getPaymentPeriod() + " dias " + creditRequest.getTypeFee();
        }
        dataResolution.setAmortization(typeAmortization);//pendiente validar

        String textRate = "";
        if(creditRequest.getBaseInterestRate()>0 && creditRequest.getInitPeriodBaseRate()>0){
            textRate =  creditRequest.getRateInterest().toString()+"% tasa durante los primeros "
                    +creditRequest.getInitPeriodBaseRate() + " meses, posteriormente se cobra el " + creditRequest.getBaseInterestRate()+"% mas la TRE ";
        }else{
            textRate = creditRequest.getRateInterest().toString()+ "% durante la vida del credito";
        }
        dataResolution.setRate(textRate);
        Integer term;
        if(creditRequest.getTypeFee().equals("PLAZO FIJO")) {
            Long lterm =  DAYS.between(creditRequest.getPaymentPlanDate(),creditRequest.getPaymentPlanEndDate());
            term = lterm.intValue();
            dataResolution.setTerm(term + " dias");
        }else{
             term = creditRequest.getTypeTerm().equals("MES") ? creditRequest.getTerm()
                    : creditRequest.getTypeTerm().equals("ANUAL") ? creditRequest.getTerm() * 12
                    : creditRequest.getTypeTerm().equals("DIA") ? creditRequest.getTerm() / 30 : 0;
            dataResolution.setTerm(String.valueOf(term) + " meses");
        }
        List<Charge> chargeList = mapper.readValue(creditRequest.getCharge(),new TypeReference<List<Charge>>(){});
        Optional<Charge> charge = Optional.ofNullable(chargeList.stream()
                .filter(value -> value.getName().equals("SEGURO DESGRAVAMEN"))
                .collect(Collectors.toList()).get(0));
        if(charge.isPresent()) {
            dataResolution.setSureRelief(charge.get().getValue()==null?0.0:charge.get().getValue() / 100);
        }else{
            dataResolution.setSureRelief(0.0);
        }

        charge = Optional.ofNullable(chargeList.stream()
                .filter(value -> value.getName().equals("SEGURO CONTRA TODO RIESGO"))
                .collect(Collectors.toList()).get(0));
        if(charge.isPresent()) {
            dataResolution.setSecureOverAllRisk(charge.get().getValue()==null?0.0:charge.get().getValue() * factor);
        }else {
            dataResolution.setSecureOverAllRisk(0.0);
        }
        dataResolution.setTeac(0.0);
        dataResolution.setSector(creditResolution.getSector());
        dataResolution.setItem(creditResolution.getItem());

        String[] productObject = getDescriptionProductAndObjectCredit(typeCredit,creditRequest.getProductCredit(),creditRequest.getObjectCredit());
        dataResolution.setCreditObject(productObject[1]);

        List<Disbursements> disbursementsList;
        disbursementsList = mapper.readValue(creditResolution.getNumberDisbursements(),
                new TypeReference<List<Disbursements>>(){});

        dataResolution.setDisbursementsList(disbursementsList);

        List<DirectIndirectDebts> directIndirectDebtsList = new ArrayList<>();
        directIndirectDebtsList = mapper.readValue(creditResolution.getDirectIndirectDebts(),new TypeReference<List<DirectIndirectDebts>>(){});
        directIndirectDebtsList = directIndirectDebtsList.stream()
                                    .sorted(Comparator.comparing(DirectIndirectDebts::getTypeDebts))
                                    .collect(Collectors.toList());
        dataResolution.setDirectIndirectsDebtsList(directIndirectDebtsList);
//        dataResolution.setIndirectDebtsList(directIndirectDebtsList.stream().filter(value -> value.getTypeDebts().equals("indirect")).collect(Collectors.toList()));

        List<Exceptions> exceptionsList = new ArrayList<>();
        exceptionsList = mapper.readValue(creditResolution.getExceptions(),new TypeReference<List<Exceptions>>(){});
        dataResolution.setExceptionsList(exceptionsList);

        if(dataResolution.getTypeResolution().equals("REGIONAL")){
            dataResolution.setMarkRegional("X");
            dataResolution.setMarkLevel2("");
            dataResolution.setMarkLevel3("");
            dataResolution.setMarkLevel1("");
        }else if (dataResolution.getTypeResolution().equals("NIVEL1")){
            dataResolution.setMarkLevel1("X");
            dataResolution.setMarkRegional("");
            dataResolution.setMarkLevel2("");
            dataResolution.setMarkLevel3("");
        }else if (dataResolution.getTypeResolution().equals("NIVEL2")){
            dataResolution.setMarkLevel2("X");
            dataResolution.setMarkRegional("");
            dataResolution.setMarkLevel1("");
            dataResolution.setMarkLevel3("");
        }else if (dataResolution.getTypeResolution().equals("NIVEL3")){
            dataResolution.setMarkLevel3("X");
            dataResolution.setMarkRegional("");
            dataResolution.setMarkLevel2("");
            dataResolution.setMarkLevel1("");
        }


        return dataResolution;
    }

    public static String[] getDescriptionProductAndObjectCredit(TypeCredit typeCredit, String codeProductTypeCredit, String codeObjectCredit) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String[] result=new String[2];
        List<ProductTypeCredit> productTypeCreditList = mapper.readValue(typeCredit.getProductTypeCredit()
                ,new TypeReference<List<ProductTypeCredit>>(){});
        List<ObjectCredit> objectCreditList = mapper.readValue(typeCredit.getObjectCredit()
               ,new TypeReference<List<ObjectCredit>>(){});

        ProductTypeCredit productTypeCredit = productTypeCreditList.stream()
                .filter(p -> p.getExternalCode().equals(Integer.valueOf(codeProductTypeCredit))).findFirst().get();
        ObjectCredit objectCredit = objectCreditList.stream()
                .filter(o -> o.getExternalCode().equals(Integer.valueOf(codeObjectCredit))).findFirst().get();
        result[0] = productTypeCredit.getDescription();
        result[1] = objectCredit.getDescription();
        return result;
    }

}
