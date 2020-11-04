package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.CompanyData;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.creditRequest.NoOwnGuarantee;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.applicant.dto.CompanyDataDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.StatementApplicants;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.ApplicantForStatementDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.PatrimonialStatementApplicantDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementApplicant {

    public PatrimonialStatementApplicantDto generatePatrimonialStatementApplicantDto(CreditRequest creditRequest
            ,List<StatementApplicants> statementApplicants, Applicant applicant, List<PatrimonialStatement> patrimonialStatementList
            , List<ApplicantForStatementDto> applicantForStatementDtoList, String typeCreditDescription, String typeRelation){

        ObjectMapper mapper = new ObjectMapper();
        PatrimonialStatementApplicantDto psa = new PatrimonialStatementApplicantDto();

        psa.setNumberRequest(creditRequest.getNumberRequest());
        psa.setCurrency(creditRequest.getCurrency());
        psa.setCurrencyName(creditRequest.getCurrency().equals("$us.")?"DOLARES":"BOLIVIANOS");
        psa.setAmount(creditRequest.getAmount());
        psa.setLiteralAmount(new NumberToLiteral().Convert(String.format("%.2f",creditRequest.getAmount()),true,
               "","float"));
        psa.setTypeCredit(creditRequest.getTypeCredit());
        psa.setNameTypeCredit(typeCreditDescription);
        psa.setTerm(creditRequest.getTerm());
        psa.setTypeTerm(getTypeTerm(creditRequest.getTypeTerm()));
        psa.setPaymentPeriod(creditRequest.getPaymentPeriod());
        psa.setTypeFee(creditRequest.getTypeFee());
        psa.setRateInterest(creditRequest.getRateInterest());
        psa.setRequestDate(creditRequest.getRequestDate());
        statementApplicants = completeStatementApplicant(creditRequest.getNoOwnGuarantee(),statementApplicants);
        psa.setGuaranteeList(statementApplicants);
        psa.setDestination(creditRequest.getDestination());
        psa.setFullIdCard(applicant.getFullIdCard());
        psa.setDependentNumber(applicant.getDependentNumber());
        psa.setNameCompanyWork(applicant.getNameCompanyWork());

        List<ApplicantForStatementDto> listDebtors = new ArrayList<>();
        List<ApplicantForStatementDto> listGuarantor = new ArrayList<>();
        List<ApplicantForStatementDto> listCoDebtors = new ArrayList<>();
        if(typeRelation.equals("deudor")) {
            listDebtors = applicantForStatementDtoList.stream()
                    .filter(a -> a.getTypeApplicant().equals("1deudor") || a.getTypeApplicant().equals("2conyuge"))
                    .sorted(Comparator.comparing(ApplicantForStatementDto::getTypeApplicant))
                    .collect(Collectors.toList());
            listGuarantor = applicantForStatementDtoList.stream()
                    .filter(a -> a.getTypeApplicant().equals("4garante"))
                    .collect(Collectors.toList());
            listCoDebtors = applicantForStatementDtoList.stream()
                    .filter(a -> a.getTypeApplicant().equals("3codeudor"))
                    .collect(Collectors.toList());

            listDebtors.addAll(listCoDebtors);
        }else if(typeRelation.equals("garante")){
            listGuarantor = applicantForStatementDtoList.stream()
                    .filter(a -> a.getTypeApplicant().equals("4garante") || a.getTypeApplicant().equals("2conyuge"))
                    .collect(Collectors.toList());
        }else if(typeRelation.equals("codeudor")){
            listCoDebtors = applicantForStatementDtoList.stream()
                    .filter(a -> a.getTypeApplicant().equals("3codeudor") || a.getTypeApplicant().equals("2conyuge"))
                    .collect(Collectors.toList());
        }

        psa.setApplicantDebtorList(listDebtors);
        psa.setApplicantGuarantorList(listGuarantor);
        psa.setApplicantCoDebtorList(listCoDebtors);

        try {
            List<CompanyData> companyDataList = mapper.readValue(applicant.getCompanyData(), new TypeReference<List<CompanyData>>(){});
            psa.setCompanyDataList(fillCompanyDataDto(applicant,companyDataList));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Double totalAssets = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double totalLiabilities = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("PASIVO"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double patrimony = totalAssets - totalLiabilities;

        psa.setTotalAssets(totalAssets);
        psa.setTotalLiabilities(totalLiabilities);
        psa.setPatrimony(patrimony);

        List<PatrimonialStatement> earningList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("INGRESOS"))
                .collect(Collectors.toList());
        psa.setEarningList(earningList);

        List<PatrimonialStatement> expenseList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("EGRESOS"))
                .collect(Collectors.toList());
        psa.setExpenseList(expenseList);

        Double totalEarning = earningList.stream()
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double totalExpense = expenseList.stream()
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();
        Double balance = totalEarning - totalExpense;
        psa.setTotalEarning(totalEarning);
        psa.setTotalExpense(totalExpense);
        psa.setBalance(balance);

        List<PatrimonialStatement> assetBankCheckingAccountList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("CUENTAS CORRIENTE Y AHORRO"))
                .collect(Collectors.toList());
        psa.setAssetBankCheckingAccountList(assetBankCheckingAccountList);

        List<PatrimonialStatement> assetReceivableList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("DOCUMENTOS Y CUENTAS POR COBRAR"))
                .collect(Collectors.toList());
        psa.setAssetReceivableList(assetReceivableList);

        List<PatrimonialStatement> assetPropertyList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("INMUEBLES"))
                .collect(Collectors.toList());
        psa.setAssetPropertyList(assetPropertyList);

        List<PatrimonialStatement> assetVehicleList = patrimonialStatementList.stream()
               .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("VEHICULO"))
               .collect(Collectors.toList());
        psa.setAssetVehicleList(assetVehicleList);

        List<PatrimonialStatement> assetOtherAsset = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("OTROS ACTIVOS"))
                .collect(Collectors.toList());
        psa.setAssetOtherAssetsList(assetOtherAsset);

        List<PatrimonialStatement> machineryList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("MAQUINARIA EQUIPO Y HERRAMIENTAS"))
                .collect(Collectors.toList());
        psa.setMachineryList(machineryList);

        List<PatrimonialStatement> cashList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("EFECTIVO"))
                .collect(Collectors.toList());
        psa.setCashList(cashList);

        List<PatrimonialStatement> inventoryList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("ACTIVO") & p.getElementCategory().equals("INVENTARIO"))
                .collect(Collectors.toList());
        psa.setInventoryList(inventoryList);

        List<PatrimonialStatement> liabilityDocumentsToPayList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("PASIVO") & p.getElementCategory().equals("DOCUMENTOS Y CUENTAS POR PAGAR"))
                .collect(Collectors.toList());
        psa.setLiabilityDocumentsToPayList(liabilityDocumentsToPayList);

        List<PatrimonialStatement> liabilityCreditCardsList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("PASIVO") & p.getElementCategory().equals("TARJETAS DE CREDITO"))
                .collect(Collectors.toList());
        psa.setLiabilityCreditCardsList(liabilityCreditCardsList);

        List<PatrimonialStatement> liabilityOtherLiabilitiesList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("PASIVO") & p.getElementCategory().equals("OTROS PASIVOS"))
                .collect(Collectors.toList());
        psa.setLiabilityOtherLiabilitiesList(liabilityOtherLiabilitiesList);


        return psa;

    }

    private String getTypeTerm(String typeTerm){
        if (typeTerm.equals("MES")) return "Meses";
        if (typeTerm.equals("ANUAL")) return "Años";
        if (typeTerm.equals("DIA")) return "Dias";

        return "";
    }

    private List<CompanyDataDto> fillCompanyDataDto(Applicant applicant, List<CompanyData> companyDataList){
        List<CompanyDataDto> companyDataDtoList = new ArrayList<>();
        CompanyDataDto companyDataDto = new CompanyDataDto();
        companyDataDto.setFullName(applicant.getFullName());
        companyDataDto.setIdentify(applicant.getFullIdCard());
        companyDataDto.setNameCompanyWork(applicant.getNameCompanyWork());
        companyDataDto.setNit(applicant.getNit());
        companyDataDto.setAddress(companyDataList.get(0).getAddress());
        companyDataDto.setBuilding(companyDataList.get(0).getBuilding());
        companyDataDto.setOffice(companyDataList.get(0).getOffice());
        companyDataDto.setCity(companyDataList.get(0).getCity());
        companyDataDto.setProvince(companyDataList.get(0).getProvince());
        companyDataDto.setBlock(companyDataList.get(0).getBlock());
        companyDataDto.setPhone(companyDataList.get(0).getPhones());
        companyDataDto.setEmail(companyDataList.get(0).getEmail());
        companyDataDto.setComercialNumber(companyDataList.get(0).getComercialNumber());
        companyDataDto.setSocietyType(companyDataList.get(0).getSocietyType());
        companyDataDto.setInitials(companyDataList.get(0).getInitials());
        companyDataDto.setAntiquityArea(companyDataList.get(0).getAntiquityArea());
        companyDataDto.setNumberEmployees(companyDataList.get(0).getNumberEmployees());
        companyDataDto.setWebPage(companyDataList.get(0).getWebpage());
        companyDataDto.setConstitutionDate(companyDataList.get(0).getConstitutionDate());

        companyDataDtoList.add(companyDataDto);
        return companyDataDtoList;
    }

    private List<StatementApplicants> completeStatementApplicant(String jsonNoOwnGuarantee, List<StatementApplicants> statementApplicants){
        ObjectMapper mapper = new ObjectMapper();
        List<NoOwnGuarantee> noOwnGuaranteeList = new ArrayList<>();
        try {
            if(jsonNoOwnGuarantee!=null) {
                noOwnGuaranteeList = mapper.readValue(jsonNoOwnGuarantee,new TypeReference<List<NoOwnGuarantee>>(){});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(NoOwnGuarantee n:noOwnGuaranteeList){
            StatementApplicants s = new StatementApplicants();
            s.setElementCategory(n.getDescription());
            s.setFieldDouble1(n.getCommercialValue());
            statementApplicants.add(s);
        }

        return statementApplicants;
    }


}
