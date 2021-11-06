package com.mindware.workflow.spring.rest.creditResolution;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.entity.config.ExchangeRate;
import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.creditRequest.NoOwnGuarantee;
import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.config.RepositoryExchangeRate;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.dto.*;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.service.task.CreateCreditResolutionCreditRequest;
import com.mindware.workflow.core.service.task.UtilPaymentPlan;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping(value = "/rest", produces = {"applicantion/json"})
public class CreditResolutionCreditRequestApplicantController {
    private static Logger LOGGER = LoggerFactory.getLogger(CreditResolutionCreditRequestApplicantController.class);

    private Integer numberRequest;
    private Integer numberApplicant;
    private UUID idCreditRequestApplicant;
    private Integer frecuency;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryCreditResolution repositoryCreditResolution;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryPaymentPlanDto repositoryPaymentPlanDto;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;

    @Autowired
    RepositoryExchangeRate repositoryExchangeRate;

    @Autowired
    RepositoryTypeCredit repositoryTypeCredit;

    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryOffice repositoryOffice;

    @GetMapping(value = "/v1/creditResolutionReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody  byte[] getCreditResolutionReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("id-credit-request-applicant")) idCreditRequestApplicant = UUID.fromString(value);
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("number-applicant")) numberApplicant = Integer.parseInt(value);
        });

        Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(numberApplicant).get();

        Optional<Applicant> spouse = repositoryApplicant.getApplicantByNumberApplicant(applicant.getNumberApplicantSpouse());

        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(idCreditRequestApplicant);
        patrimonialStatementList = patrimonialStatementList.stream()
                .filter(p -> Objects.nonNull(p.getCategory().equals("ACTIVO") && p.getElementCategory().equals("CUENTAS CORRIENTE Y AHORRO"))
                        && p.getFieldBoolean4()!=null && p.getFieldBoolean4().equals("SI"))
                .collect(Collectors.toList());
        Double reciprocity = patrimonialStatementList.stream()
                .map(p -> p.getFieldDouble1()).reduce(0.0,Double::sum);
        CreditResolution creditResolution = repositoryCreditResolution.getByNumberRequest(numberRequest).get();
        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();

        List<UnsecuredGuarantee> unsecuredGuarantee = new ArrayList<>();

        if(creditRequest.getTypeGuarantee().equals("IPQ")) {
            UnsecuredGuarantee unsecuredGuarantee1 = new UnsecuredGuarantee();
            unsecuredGuarantee1.setUnsecuredGuarantee(creditRequest.getTypeGuarantee().concat(" - GARANTIA QUIROGRAFARIA - SOLA FIRMA"));
            unsecuredGuarantee.add(unsecuredGuarantee1);
        }

        Office office = repositoryOffice.getOfficeByInternalCode(creditRequest.getIdOffice()).get();
        Users users = repositoryUsers.getUserByIdUser(creditRequest.getLoginUser()).get();

        Double factor = creditRequest.getPaymentPeriod()/30.0;
        TypeCredit typeCredit = repositoryTypeCredit.getByExternalCode(creditRequest.getTypeCredit()).get();
        List<PaymentPlanDto> paymentPlantDtoList = new ArrayList<>();
        paymentPlantDtoList = repositoryPaymentPlanDto.getDataReportPaymentPlant(numberRequest);
        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);
        double[] payments = UtilPaymentPlan.getListFee(paymentPlanList,paymentPlantDtoList.get(0).getAmount());
        Double teac=0.0;
        if(creditRequest.getTypeFee().equals("PLAZO FIJO")) {
            Long days = DAYS.between(creditRequest.getPaymentPlanDate(),creditRequest.getPaymentPlanEndDate());
            factor = days.intValue()/30.0;
            teac = UtilPaymentPlan.irr3(payments, 0.0001d)  * 11.8356;

        }else{
            teac = UtilPaymentPlan.irr3(payments, 0.0001d) * 11.8356;//11.8356;
        }
        teac = teac/factor;

        CreditResolutionCreditRequestApplicant result = CreateCreditResolutionCreditRequest.generate(applicant,spouse,creditResolution,creditRequest,typeCredit);
        result.setNameOffice(office.getName());
        result.setTeac(teac);
        result.setPatrimony(getPatrimony(idCreditRequestApplicant));
        result.setGuarantorResolutionList(getGuarantors(numberRequest));
        result.setReciprocity(reciprocity);
        result.setNumberCreditRequest(numberRequest);
        result.setNameOfficer(users.getFullName());
        result.setUnsecuredGuarantee(unsecuredGuarantee);

        List<GuaranteesResolution> guaranteesResolutionList = getGuarantees(numberRequest);
        guaranteesResolutionList.addAll(getNoOwnGuarantee(creditRequest.getNoOwnGuarantee()));
        result.setGuaranteesResolutionList(guaranteesResolutionList);

        List<CodebtorResolution> codebtorResolutionList = getCodebtorsResolution(numberRequest);
        result.setCodebtorsList(codebtorResolutionList);

        Double amount = creditRequest.getAmount();
        ExchangeRate exchangeRate = repositoryExchangeRate.getActiveExchangeRateByCurrency("$us.").get();
        Double exchange = exchangeRate.getExchange();
//        if(creditRequest.getCurrency().equals("BS"))  amount = Math.round((amount/exchange)*100.0)/100.0;
        result.setIndicatorsList(getIndicators(amount,paymentPlanList,guaranteesResolutionList,factor.intValue(),creditRequest.getTypeFee(),creditResolution));

        InputStream stream = getClass().getResourceAsStream("/template-report/creditResolution/creditResolution.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
//        String pathSubreport = getClass().getResource("/template-report/creditResolution/").getPath();
        String pathSubreport = "template-report/creditResolution/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<CreditResolutionCreditRequestApplicant> collection = new ArrayList<>();
        collection.add(result);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    private List<GuaranteesResolution> getGuarantees(Integer numberRequest){
        List<CreditRequestApplicant> creditRequestApplicants = repositoryCreditRequestApplicant.getByNumberRequest(numberRequest);
        List<GuaranteesResolution> guaranteesResolutionList = new ArrayList<>();
        for(CreditRequestApplicant cr : creditRequestApplicants){
            List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(cr.getId());
            patrimonialStatementList = patrimonialStatementList.stream().filter(value ->
                    value.getCategory().equals("ACTIVO") && value.getFieldBoolean2()!=null )
                    .collect(Collectors.toList());

            for(PatrimonialStatement p:patrimonialStatementList){
                if(p.getFieldBoolean2().equals("SI")){
                   GuaranteesResolution guaranteesResolution = new GuaranteesResolution();
                   guaranteesResolution.setDescription(p.getFieldText1());
                   guaranteesResolution.setMortgage(p.getFieldDouble3());
                   guaranteesResolution.setCommercialValue(p.getFieldDouble1());
                   guaranteesResolution.setAppraisalDate(p.getFieldDate2());
                   guaranteesResolution.setGuaranteeAmount(p.getFieldDouble2());
                   guaranteesResolution.setMortgageGrade(p.getFieldSelection2());
                   guaranteesResolution.setProficient(p.getFieldText15());
                   guaranteesResolutionList.add(guaranteesResolution);

                }
            }
        }
        return guaranteesResolutionList;
    }

    private List<GuaranteesResolution> getNoOwnGuarantee(String jsonNoOwnGuarantee) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<NoOwnGuarantee> noOwnGuaranteeList = mapper.readValue(jsonNoOwnGuarantee,new TypeReference<List<NoOwnGuarantee>>(){});
        List<GuaranteesResolution> guaranteesResolutions = new ArrayList<>();

        for(NoOwnGuarantee n:noOwnGuaranteeList){
            GuaranteesResolution guaranteesResolution = new GuaranteesResolution();
            guaranteesResolution.setDescription(n.getDescription());
            guaranteesResolution.setAppraisalDate(n.getAppraisalDate());
            guaranteesResolution.setCommercialValue(n.getCommercialValue());
            guaranteesResolution.setGuaranteeAmount(n.getGuaranteeAmount());
            guaranteesResolution.setMortgage(n.getMortgageValue());
            guaranteesResolution.setMortgageGrade(n.getMortgageDegree());
            guaranteesResolution.setProficient(n.getProficient());
            guaranteesResolutions.add(guaranteesResolution);
        }
        return guaranteesResolutions;
    }

    private List<GuarantorResolution> getGuarantors(Integer numberRequest){
        List<CreditRequestApplicant> creditRequestApplicants = repositoryCreditRequestApplicant.getByNumberRequest(numberRequest);
        List<GuarantorResolution> guaranteesResolutionList = new ArrayList<>();
        for(CreditRequestApplicant cr:creditRequestApplicants){
            if(cr.getTypeRelation().equals("garante")){
                Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(cr.getNumberApplicant()).get();
                GuarantorResolution guarantorResolution = new GuarantorResolution();
                guarantorResolution.setFullName(applicant.getFullName());
                guarantorResolution.setIdCardComplete(applicant.getFullIdCard());
                guarantorResolution.setPatrimony(getPatrimony(cr.getId()));
                guarantorResolution.setHomeAddress(applicant.getHomeaddress());
                guaranteesResolutionList.add(guarantorResolution);
            }
        }
        return guaranteesResolutionList;
    }

    private List<CodebtorResolution> getCodebtorsResolution(Integer numberRequest){
        List<CreditRequestApplicant> creditRequestApplicants = repositoryCreditRequestApplicant.getByNumberRequest(numberRequest);
        List<CodebtorResolution> codebtorResolutionList = new ArrayList<>();
        for(CreditRequestApplicant cr: creditRequestApplicants){
            if(cr.getTypeRelation().equals("codeudor")){
                Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(cr.getNumberApplicant()).get();
                CodebtorResolution codebtorResolution = new CodebtorResolution();
                codebtorResolution.setFullNameCodebtor(applicant.getFullName());
                codebtorResolution.setIdCardCompleteCodebtor(applicant.getFullIdCard());
                Optional<Applicant> spouse = repositoryApplicant.getApplicantByNumberApplicant(applicant.getNumberApplicantSpouse());
                if(spouse.isPresent()){
                    codebtorResolution.setFullNameSpouse(spouse.get().getFullName());
                    codebtorResolution.setIdCardCompleteSpouse(spouse.get().getFullIdCard());
                }
                codebtorResolution.setHomeAddress(applicant.getHomeaddress());
                codebtorResolution.setPhones(applicant.getHomephone() +" " + applicant.getCellphone());
                codebtorResolution.setCaedecCodebtor(applicant.getCaedec());
                Double patrimony = getPatrimony(cr.getId());
                codebtorResolution.setPatrimony(patrimony);
                codebtorResolution.setCustomerFrom(applicant.getCustomerFrom());

                List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(cr.getId());
                if(patrimonialStatementList.size() > 0) {
                    patrimonialStatementList = patrimonialStatementList.stream()
                            .filter(p -> Objects.nonNull(p.getCategory().equals("ACTIVO") && p.getElementCategory().equals("CUENTAS CORRIENTE Y AHORRO"))
                                    && p.getFieldBoolean4() != null && p.getFieldBoolean4().equals("SI"))
                            .collect(Collectors.toList());
                    Double reciprocity = patrimonialStatementList.stream()
                            .map(p -> p.getFieldDouble1()).reduce(0.0, Double::sum);
                    codebtorResolution.setReciprocity(reciprocity);
                }else{
                    codebtorResolution.setReciprocity(0.0);
                }

                codebtorResolutionList.add(codebtorResolution);
            }
        }

        return codebtorResolutionList;
    }

    private List<Indicators> getIndicators(Double amount, List<PaymentPlan> paymentPlanList,
                                           List<GuaranteesResolution> guaranteesResolutionList,
                                           int factor, String typeFee, CreditResolution creditResolution) throws IOException {
        List<Indicators> indicatorsList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Indicators indicators = new Indicators();
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(idCreditRequestApplicant);
//        Double totalDirectDebts = patrimonialStatementList.stream()
//                .filter(p -> p.getCategory().equals("PASIVO"))
//                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();

        List<DirectIndirectDebts> directIndirectDebts = mapper.readValue(creditResolution.getDirectIndirectDebts(),new TypeReference<List<DirectIndirectDebts>>(){});
        Double totalDirectDebts = directIndirectDebts.stream()
                .filter(d -> d.getTypeDebts().equals("direct"))
                .mapToDouble(DirectIndirectDebts::getAmount).sum();

        Double totalAsset = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();

        Double totalMortageValue = guaranteesResolutionList.stream()
                .filter(p -> p.getMortgage()!=null)
                .mapToDouble(GuaranteesResolution::getMortgage).sum();

        Double totalGuarantee = guaranteesResolutionList.stream()
                .filter(p -> p.getGuaranteeAmount()!=null)
                .mapToDouble(GuaranteesResolution::getGuaranteeAmount).sum();

        Double totalIncome = (patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("INGRESOS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum())*factor;

        Double totalFeeOtherEntities = (patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("EGRESOS") && p.getElementCategory().equals("PAGO DEUDAS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum())*factor;

        Double fee = getFee(paymentPlanList);

        Double patrimony = totalAsset - totalDirectDebts;

        indicators.setIndicator1(totalDirectDebts);
        indicators.setIndicator2(totalMortageValue/totalDirectDebts);
        indicators.setIndicator3(totalMortageValue/amount);
        indicators.setIndicator4(fee);
        indicators.setIndicator5(totalIncome);
        if(!typeFee.equals("PLAZO FIJO")) {
            indicators.setIndicator6((fee + totalFeeOtherEntities) / totalIncome);

            indicators.setIndicator7(fee / totalIncome);
        }
        indicators.setIndicator8(patrimony);
        indicators.setIndicator9(patrimony/totalDirectDebts);
        indicators.setIndicator10(patrimony/totalDirectDebts);
        indicators.setIndicator11(totalDirectDebts);
        indicators.setIndicator12(totalGuarantee);
        indicators.setIndicator13(totalGuarantee/amount);



        indicatorsList.add(indicators);

        return indicatorsList;
    }

    private Double getPatrimony(UUID idCreditRequestApplicant){
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(idCreditRequestApplicant);
        Double leabilities = 0.0;
        Double assets = 0.0;
        for(PatrimonialStatement p : patrimonialStatementList){
            if(p.getCategory().equals("ACTIVO")){
                assets = assets + p.getFieldDouble1();
            }else if(p.getCategory().equals("PASIVO")){
                leabilities = leabilities + p.getFieldDouble1();
            }

        }

        return assets - leabilities;
    }

    private Double getFee(List<PaymentPlan> paymentPlanList){
        PaymentPlan paymentPlan = paymentPlanList.stream()
                .filter(p ->p.getCapital()>0.0 && p.getInterest()>0.0)
                .findFirst().get();
        return paymentPlan.getFee();
    }
}
