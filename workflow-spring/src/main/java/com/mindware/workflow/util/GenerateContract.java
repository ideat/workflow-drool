package com.mindware.workflow.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.Charge;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.config.Signatories;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.dto.CreditRequestApplicantdto;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.dto.PaymentPlanDto;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import com.mindware.workflow.core.service.task.NumberToLiteral;
import com.mindware.workflow.core.service.task.UtilPaymentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GenerateContract {
    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryPaymentPlanDto repositoryPaymentPlanDto;

    @Autowired
    RepositoryParameter repositoryParameter;

    @Autowired
    RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto;

    @Autowired
    RepositoryOffice repositoryOffice;

    @Autowired
    RepositoryTypeCredit repositoryTypeCredit;

    private CreditRequest creditRequest;
    private List<CreditRequestApplicantdto> creditRequestApplicantdtoGuarantorsList;
    private List<CreditRequestApplicantdto> creditRequestApplicantdtoCodebtorsList;
    private List<CreditRequestApplicantdto> creditRequestApplicantdtoDebtorList;

    private String detail;
    private String temp;
    private Map<String,String> getCreditRequestFields(Integer numberRequest) throws IOException {
        Map<String,String> mapCreditRequestFields = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        NumberToLiteral numberToLiteral = new NumberToLiteral();

        creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        List<PaymentPlanDto> paymentPlantDtoList = repositoryPaymentPlanDto.getDataReportPaymentPlant(numberRequest);
        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);
        Parameter parameter = repositoryParameter.getParameterByCategoryAndValue("PRODUCTO",creditRequest.getProductCredit()).get();

        double[] payments = UtilPaymentPlan.getListFee(paymentPlanList,paymentPlantDtoList.get(0).getAmount());
        Double teac = UtilPaymentPlan.irr(payments, 0.01d) * 12;

        mapCreditRequestFields.put("monto",String.format("%,.2f",creditRequest.getAmount()));
        mapCreditRequestFields.put("plazo",String.valueOf(creditRequest.getTerm()));
        mapCreditRequestFields.put("simboloMoneda",creditRequest.getCurrency());
        if(creditRequest.getCurrency().equals("$us.")){
            mapCreditRequestFields.put("nombreMoneda","Dolares");
        }else{
            mapCreditRequestFields.put("nombreMoneda","Bolivianos");
        }

        String typeTerm = creditRequest.getTypeTerm().equals("MES")?"Meses"
                :creditRequest.getTypeTerm().equals("DIA")?"Dias":"Años";
        mapCreditRequestFields.put("tipoPlazo",typeTerm);
        mapCreditRequestFields.put("periodoPago",String.valueOf(creditRequest.getPaymentPeriod()));
        mapCreditRequestFields.put("diaFijo",String.valueOf(creditRequest.getFixedDay()));
        mapCreditRequestFields.put("tipoCuota",creditRequest.getTypeFee());
        mapCreditRequestFields.put("tasaInteres",creditRequest.getRateInterest().toString());
        mapCreditRequestFields.put("tasaBaseInteres",creditRequest.getBaseInterestRate().toString());
        mapCreditRequestFields.put("inicioPeriodoBase",creditRequest.getInitPeriodBaseRate().toString());
        mapCreditRequestFields.put("tasaTeac",teac.toString());
        mapCreditRequestFields.put("fechaSolicitud",creditRequest.getRequestDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        mapCreditRequestFields.put("destino",creditRequest.getDestination());
        mapCreditRequestFields.put("productoCredito", parameter.getDescription());
//        parameter = repositoryParameter.getParameterByCategoryAndValue("TIPO CREDITO",creditRequest.getTypeCredit()).get();
        TypeCredit typeCredit = repositoryTypeCredit.getByExternalCode(creditRequest.getTypeCredit()).get();
        mapCreditRequestFields.put("tipoCredito", typeCredit.getDescription());
        mapCreditRequestFields.put("cajaAhorro", creditRequest.getSavingBox());


        creditRequestApplicantdtoGuarantorsList = repositoryCreditRequestApplicantDto.getByNumberRequestTypeRelation(numberRequest,"garante");
        creditRequestApplicantdtoCodebtorsList = repositoryCreditRequestApplicantDto.getByNumberRequestTypeRelation(numberRequest,"codeudor");
        creditRequestApplicantdtoDebtorList = repositoryCreditRequestApplicantDto.getByNumberRequestTypeRelation(numberRequest,"deudor");
        int i=1;
        for(CreditRequestApplicantdto cra:creditRequestApplicantdtoGuarantorsList){
            mapCreditRequestFields.put("nombreGarante"+i,cra.getFullName()==null?"":cra.getFullName());
            mapCreditRequestFields.put("nombreGranteConyuge"+i,cra.getFullNameSpouse()==null?"":cra.getFullNameSpouse());
            i++;
        }
        i=1;
        for(CreditRequestApplicantdto cra:creditRequestApplicantdtoDebtorList){
            mapCreditRequestFields.put("nombreDeudor"+i,cra.getFullName()==null?"":cra.getFullName());
            mapCreditRequestFields.put("nombreDeudorConyuge"+i,cra.getFullNameSpouse()==null?"":cra.getFullNameSpouse());
            i++;
        }
        i=1;
        for(CreditRequestApplicantdto cra:creditRequestApplicantdtoCodebtorsList){
            mapCreditRequestFields.put("nombreDeudor"+i,cra.getFullName()==null?"":cra.getFullName());
            mapCreditRequestFields.put("nombreDeudorConyuge"+i,cra.getFullNameSpouse()==null?"":cra.getFullNameSpouse());
            i++;
        }


        Office office = repositoryOffice.getOfficeByInternalCode(creditRequest.getIdOffice()).get();
        mapCreditRequestFields.put("direccionOficina",office.getAddress());
        mapCreditRequestFields.put("ciudadOficina",office.getCity());


        List<Signatories> signatoriesList = mapper.readValue(office.getSignatorie(),new TypeReference<Signatories>(){});
        signatoriesList = signatoriesList.stream().filter(v -> v.getState().equals("ACTIVO")).collect(Collectors.toList());
        for(Signatories s:signatoriesList){
            mapCreditRequestFields.put("nombreRepresentante"+s.getPriority(),s.getName());
            mapCreditRequestFields.put("carnetRepresentante"+s.getPriority(),s.getIdentifyCard());
            mapCreditRequestFields.put("nroNotaria"+s.getPriority(),s.getNumNotary());
            mapCreditRequestFields.put("nombreNotario"+s.getPriority(),s.getNameNotary());
            mapCreditRequestFields.put("ditritoJudicial"+s.getPriority(),s.getJudicialDistrict());
            mapCreditRequestFields.put("nroTestimonio"+s.getPriority(),s.getNumTestimony());
            mapCreditRequestFields.put("fechaTestimonio"+s.getPriority(),s.getDateTestimony());
        }

        mapCreditRequestFields.put("literalMonto", numberToLiteral.Convert(creditRequest.getAmount().toString(),true,"","float"));
        mapCreditRequestFields.put("literalPlazo", numberToLiteral.Convert(creditRequest.getAmount().toString(),true,"",""));

        String paymentFrecuency ="";
        if(creditRequest.getPaymentPeriod()==30){
            paymentFrecuency = "MENSUALES";
        }else if(creditRequest.getPaymentPeriod()==60){
            paymentFrecuency = "BIMESTRALES";
        }else if(creditRequest.getPaymentPeriod()==90){
            paymentFrecuency= "TRIMESTRALES";
        }else if(creditRequest.getPaymentPeriod()==120){
            paymentFrecuency = "CUATRIMESTRALES";
        }else if(creditRequest.getPaymentPeriod()==180){
            paymentFrecuency = "SEMESTRALES";
        }else if(creditRequest.getPaymentPeriod()==360){
            paymentFrecuency = "ANUALES";
        }
        //TODO AUMENTAR VARIABLE modalidad literal (MENSUAL, TRIMESTRA, ETC)

        mapCreditRequestFields.put("frecuenciaPago",paymentFrecuency);

        List<Charge> chargeList = mapper.readValue(creditRequest.getCharge(),new TypeReference<Charge>(){});
        chargeList = chargeList.stream().filter(c -> c.isSelected()==true).collect(Collectors.toList());
        for(Charge c : chargeList){
            if(c.getName().equals("SEGURO DESGRAVAMENT")) mapCreditRequestFields.put("seguroDesgravamen",c.getValue().toString());
            if(c.getName().equals("SEGURO CONTRA INCENIO")) mapCreditRequestFields.put("seguroContraIncendio",c.getValue().toString());
        }

        Double totalPayment = Arrays.stream(payments).sum();
        mapCreditRequestFields.put("totalPagar", totalPayment.toString());
        mapCreditRequestFields.put("literalTotalPagar",numberToLiteral.Convert(totalPayment.toString(),true,"","float"));
        int totalDeudores = creditRequestApplicantdtoDebtorList.size() + creditRequestApplicantdtoCodebtorsList.size();
        mapCreditRequestFields.put("denominacionDeudor",totalDeudores>1?"DEUDORES":"DEUDOR(A)");

        mapCreditRequestFields.put("redaccionDeudor", replaceRedactionDebtorsGuarantors("#redaccionDeudor"));

        return mapCreditRequestFields;

    }

    private String replaceRedactionDebtorsGuarantors(String value){
        Map<String,String> map = new HashMap<>();
        Parameter parameter = repositoryParameter.getParameterByCategoryAndValue("VARIABLE_CONTRATOS",value).get();

        int i=0;
        for(CreditRequestApplicantdto cd:creditRequestApplicantdtoDebtorList ){
            map.put("@{nombre}",cd.getFullName());
            map.put("@{carnet}",cd.getFullIdCard());
            map.put("@{estadoCivil}",cd.getCivilStatus());
            map.put("@{direccionDomicilio}", cd.getTypeHome() + " en, " +cd.getHomeaddress() + " de la ciudad" + cd.getCity()
                            + ", provincia " + cd.getProvince() + " manzano " + cd.getBlock());
            temp = parameter.getDescription();
            map.forEach((k, v) -> {
                temp.replaceAll(Pattern.quote(k), v);
            });
            if(i==0) {
                detail = temp;
            }else{
                detail = detail + "\n" + temp;
            }
            if(cd.getIdCardSpouse()!=null){
                map.replace("@{nombre}",cd.getFullName(),cd.getFullNameSpouse());
                map.replace("@{carnet}",cd.getFullIdCard(),cd.getFullIdCardSpouse());
                map.replace("@{estadoCivil}",cd.getCivilStatus(),cd.getCivilStatusSpouse());
                map.replace("@{direccionDomicilio}", cd.getTypeHome() + " en, " +cd.getHomeaddress() + " de la ciudad" + cd.getCity()
                        + ", provincia " + cd.getProvince() + " manzano " + cd.getBlock()
                ,cd.getTypeHomeSpouse() + " en, " +cd.getHomeaddressSpouse() + " de la ciudad" + cd.getCitySpouse()
                                + ", provincia " + cd.getProvinceSpouse() + " manzano " + cd.getBlockSpouse());
                temp = parameter.getDescription();
                map.forEach((k, v) -> {
                    temp.replaceAll(Pattern.quote(k), v);
                });
                detail = detail + "\n" + temp;
            }

            i++;
        }


        return detail;
    }


}
