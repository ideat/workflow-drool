package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.entity.creditRequest.NoOwnGuarantee;
import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import com.mindware.workflow.core.service.data.observation.dto.ObservationCreditRequestApplicant;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CreateObservationCreditRequestApplicant {

    public ObservationCreditRequestApplicant generate(Observation observation, List<PatrimonialStatement> patrimonialStatementList,
                                                      Applicant applicant, Users users, CreditRequest creditRequest,
                                                      Map<String, String> mapData, List<PaymentPlan> paymentPlanList){
        ObservationCreditRequestApplicant result = new ObservationCreditRequestApplicant();
        result.setNumberApplicant(applicant.getNumberApplicant());
        result.setNumberRequest(observation.getNumberRequest());
        result.setNameOfficial(users.getNames());
        result.setFullNameClient(applicant.getFullName());
        result.setTypeCredit(mapData.get("typeCredit"));
        result.setTypeGuarantee(creditRequest.getTypeGuarantee());
        result.setMainActivity(mapData.get("mainActivity"));
        result.setAgency(mapData.get("agency"));
        result.setNumberCredit(creditRequest.getNumberCredit());////
        result.setRateInterest(creditRequest.getRateInterest());
        result.setAmount(creditRequest.getAmount());
        result.setCurrency(creditRequest.getCurrency().equals("$us.")?"DOLARES":"BOLIVIANOS");
        result.setDestination(creditRequest.getDestination());
        result.setState(creditRequest.getState());

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<TemplateObservation> observationList = Arrays.asList(mapper.readValue(observation.getObservations(),TemplateObservation[].class));
            observationList = observationList.stream()
                    .sorted(Comparator.comparing(TemplateObservation::getCondition))
                    .collect(Collectors.toList());
            result.setObservations(observationList);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Double totalAssets = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double totalLiabilities = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("PASIVO"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double totalEarning = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("INGRESOS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
//        Double totalExpenses = patrimonialStatementList.stream()
//                .filter(p -> p.getCategory().equals("EGRESOS"))
//                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();

        Double totalExpenses = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("EGRESOS") && p.getElementCategory().equals("PAGO DEUDAS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();


        Double patrimony = totalAssets - totalLiabilities;
        Integer period = creditRequest.getPaymentPeriod();
        Double factor = period/30.0;

        PaymentPlan paymentPlan = paymentPlanList.stream()
                .filter(p -> p.getInterest()>0.0)
                .findFirst().get();

        List<NoOwnGuarantee> noOwnGuaranteeList = new ArrayList<>();
        String jsonNoOwnGuarantee = creditRequest.getNoOwnGuarantee();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            noOwnGuaranteeList = objectMapper.readValue(jsonNoOwnGuarantee,new TypeReference<List<NoOwnGuarantee>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double mountGuaranteeNoOwn = noOwnGuaranteeList.stream()
                .mapToDouble(NoOwnGuarantee::getGuaranteeAmount).sum();

        Double fee = paymentPlan.getFee()/factor;

        Double mountGuarantee =  patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") && p.getFieldBoolean2()!=null && p.getFieldBoolean2().equals("SI") && p.getFieldDouble2()!=null)
                .mapToDouble(PatrimonialStatement::getFieldDouble2).sum();

        mountGuarantee = mountGuarantee + mountGuaranteeNoOwn;

        Double indicator1 = Math.round((fee/totalEarning)*10000.0)/10000.0;
        Double indicator2 = Math.round(((fee + totalExpenses)/totalEarning)*10000.0)/10000.0;

        Double indicator3 = Math.round((patrimony/creditRequest.getAmount())*100.0)/100.0;
        Double indicator4 = Math.round((mountGuarantee/creditRequest.getAmount())*100.0)/100.0;

        result.setIndicator1(indicator1);
        result.setIndicator2(indicator2);
        result.setIndicator3(indicator3);
        result.setIndicator4(indicator4);

        return result;
    }


}
