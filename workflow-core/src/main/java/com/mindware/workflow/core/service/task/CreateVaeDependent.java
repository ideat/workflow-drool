package com.mindware.workflow.core.service.task;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.patrimonialStatement.SalaryAnalisys;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeDependent.SalaryAnalysisDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeDependent.VaeDependentReportDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateVaeDependent {
    public static VaeDependentReportDto generate(List<SalaryAnalisys> salaryAnalisysList
            , List<PatrimonialStatement> patrimonialStatementList, List<Parameter> parameterList
            , Applicant applicant, List<PaymentPlan> paymentPlanList) {
        String[] typeClient = {"CLIENTE","CONYUGE","CODEUDOR 1","CODEUDOR 2","CODEUDOR 3","CODEUDOR 4"};
        VaeDependentReportDto vaeDependentReportDto = new VaeDependentReportDto();

        vaeDependentReportDto.setFullName(applicant.getFullName());
        vaeDependentReportDto.setNameCompanyWork(applicant.getNameCompanyWork());
        vaeDependentReportDto.setDependentNumber(applicant.getDependentNumber());
        vaeDependentReportDto.setProfession(applicant.getProfession());

        List<PatrimonialStatement> aux = patrimonialStatementList.stream()
                .filter(p -> !p.getElementCategory().equals("GASTOS BASICOS"))
                .collect(Collectors.toList());

        vaeDependentReportDto.setExpensesPatrimonialStatementList(aux);

        aux = patrimonialStatementList.stream()
                .filter(p -> p.getElementCategory().equals("GASTOS BASICOS"))
                .collect(Collectors.toList());
        vaeDependentReportDto.setBasicExpensesPatrimonialStatementList(aux);

        List<SalaryAnalysisDto> salaryAnalysisDtoList = new ArrayList<>();
        for(String s:typeClient){
            SalaryAnalysisDto salaryAnalysisDto = getSalaryDtoByMonth(salaryAnalisysList,s);
            if(salaryAnalysisDto!=null) {
                salaryAnalysisDtoList.add(salaryAnalysisDto);
            }
        }
        vaeDependentReportDto.setSalaryAnalysisDtoList(salaryAnalysisDtoList);

        Double totalAverage = salaryAnalysisDtoList.stream()
                              .mapToDouble(SalaryAnalysisDto::getMountlyAverage).sum();

        vaeDependentReportDto.setTotalIncome(totalAverage);

        Double value = calculateAvailable(parameterList,totalAverage,"Disponible Consumo Deb. Gar");
        vaeDependentReportDto.setDulyGuaranteed(value);

        value = calculateAvailable(parameterList,totalAverage,"Disponible Consumo No Deb. Gar.");
        vaeDependentReportDto.setDulyNoGuranteed(value);

        value = calculate(parameterList,totalAverage,"Sin Alquiler Cuota Maxima 5.5%-Con Alquiler Cuota Maxima 6%");
        vaeDependentReportDto.setNoRentMaximumFee1(value);

        value = calculate(parameterList,totalAverage,"Sin Alquiler Cuota Maxima 6%-Con Alquiler Cuota Maxima 6.5%");
        vaeDependentReportDto.setNoRentMaximumFee2(value);

        value = calculate(parameterList,totalAverage,"Sin Alquiler Cuota Maxima 6.5%");
        vaeDependentReportDto.setNoRentMaximumFee3(value);

        value = calculate(parameterList,totalAverage,"Con Alquiler Cuota Maxima 5.5%");
        vaeDependentReportDto.setRentMaximumFee1(value);

        value = calculate(parameterList,totalAverage,"Sin Alquiler Cuota Maxima 5.5%-Con Alquiler Cuota Maxima 6%");
        vaeDependentReportDto.setRentMaximumFee2(value);

        value = calculate(parameterList,totalAverage,"Sin Alquiler Cuota Maxima 6%-Con Alquiler Cuota Maxima 6.5%");
        vaeDependentReportDto.setRentMaximumFee3(value);

        vaeDependentReportDto.setFee(calculateFee(paymentPlanList));
        vaeDependentReportDto.setRatio(Math.round((vaeDependentReportDto.getFee()/totalAverage)*10000.0)/10000.0);

        Double totalExpenses = patrimonialStatementList.stream().filter(p -> p.getElementCategory().equals("PAGO DEUDAS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();

        vaeDependentReportDto.setMaxFeeDulyGuarantee(vaeDependentReportDto.getDulyGuaranteed()-totalExpenses);
        vaeDependentReportDto.setMaxFeeNoDulyGuarantee(vaeDependentReportDto.getDulyNoGuranteed() - totalExpenses);

        Double basicExpense = patrimonialStatementList.stream().filter(p -> p.getElementCategory().equals("GASTOS BASICOS"))
                .mapToDouble(PatrimonialStatement::getFieldDouble1).sum();

        Double availableAmout = totalAverage - (basicExpense+totalExpenses);
        vaeDependentReportDto.setAvailableAfterExpenses(availableAmout);

        vaeDependentReportDto.setSuperAvit(availableAmout-vaeDependentReportDto.getFee());

        return vaeDependentReportDto;
    }



    private static Double calculate(List<Parameter> parameterList, Double total, String filter){
        Parameter parameter = new Parameter();
        parameter = parameterList.stream().filter(p -> p.getDescription().equals(filter))
                .findFirst().get();
        Double aux = total/Double.parseDouble(parameter.getValue());
        return Math.round(aux*100)/100.0;
    }

    private static Double calculateAvailable(List<Parameter> parameterList, Double total, String filter){
        Parameter parameter = new Parameter();
        parameter = parameterList.stream().filter(p -> p.getDescription().equals(filter))
                .findFirst().get();
        Double aux = total*Double.parseDouble(parameter.getValue());
        return Math.round(aux*100)/100.0;
    }

    private static Double calculateFee(List<PaymentPlan> paymentPlanList){
        Double fee=0.0;
//        int count = 0;
        PaymentPlan paymentPlan = paymentPlanList.stream()
                .filter(p -> p.getInterest()>0.0 && p.getCapital()>0.0).findFirst().get();
        fee = paymentPlan.getFee(); // paymentPlan.getCapital()+paymentPlan.getInterest()+paymentPlan.getSecureCharge()+paymentPlan.getOtherCharge();
//        count = paymentPlanList.size()-1;
//        fee = Math.round((fee/count)*100.0)/100.0;


        return fee;
    }

    public static SalaryAnalysisDto getSalaryDtoByMonth(List<SalaryAnalisys> salaryAnalisysList, String typeClient){
        List<SalaryAnalisys> salaryAnalisysTypeClient = salaryAnalisysList.stream()
                .filter(p ->  p.getTypeClient().equals(typeClient))
                .collect(Collectors.toList());
        SalaryAnalysisDto salaryAnalysisDto = new SalaryAnalysisDto();
        for (SalaryAnalisys s:salaryAnalisysTypeClient){
            if(s.getNumberMonth().equals("Mes 1")){
                if(s.getPriority().equals(0)) salaryAnalysisDto.setSalary1(s.getAmount());
                if(s.getPriority().equals(1)) salaryAnalysisDto.setLawDiscounts1(s.getAmount());
                if(s.getPriority().equals(2)) salaryAnalysisDto.setOtherDiscounts1(s.getAmount());
                if(s.getPriority().equals(3)) salaryAnalysisDto.setOtherIncomes1(s.getAmount());
                if(s.getPriority().equals(4)) salaryAnalysisDto.setAvailableSalary1(s.getAmount());
            }
            if(s.getNumberMonth().equals("Mes 2")){
                if(s.getPriority().equals(0)) salaryAnalysisDto.setSalary2(s.getAmount());
                if(s.getPriority().equals(1)) salaryAnalysisDto.setLawDiscounts2(s.getAmount());
                if(s.getPriority().equals(2)) salaryAnalysisDto.setOtherDiscounts2(s.getAmount());
                if(s.getPriority().equals(3)) salaryAnalysisDto.setOtherIncomes2(s.getAmount());
                if(s.getPriority().equals(4)) salaryAnalysisDto.setAvailableSalary2(s.getAmount());
            }
            if(s.getNumberMonth().equals("Mes 3")){
                if(s.getPriority().equals(0)) salaryAnalysisDto.setSalary3(s.getAmount());
                if(s.getPriority().equals(1)) salaryAnalysisDto.setLawDiscounts3(s.getAmount());
                if(s.getPriority().equals(2)) salaryAnalysisDto.setOtherDiscounts3(s.getAmount());
                if(s.getPriority().equals(3)) salaryAnalysisDto.setOtherIncomes3(s.getAmount());
                if(s.getPriority().equals(4)) salaryAnalysisDto.setAvailableSalary3(s.getAmount());
            }
        }
        if(salaryAnalisysTypeClient.size()>0) {
            salaryAnalysisDto.setTypeClient(typeClient);

            Double average = (salaryAnalysisDto.getAvailableSalary1() + salaryAnalysisDto.getAvailableSalary2() + salaryAnalysisDto.getAvailableSalary3())/3.0;
            average = Math.round(average * 100.0) / 100.0;
            salaryAnalysisDto.setMountlyAverage(average);

            return salaryAnalysisDto;
        }else{
            return null;
        }
    }
}
