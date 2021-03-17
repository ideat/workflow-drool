package com.mindware.workflow.spring.rest.patrimonialStatement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.patrimonialStatement.SalaryAnalisys;
import com.mindware.workflow.core.entity.patrimonialStatement.SalesHistory;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.costProduct.PatrimonialStatementCostProducts;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.PatrimonialStatementProductionInventory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.inventoryProductionSales.PatrimonialStatementSalesInventory;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sales.PatrimonialStatementSalesHistoryDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeDependent.VaeDependentReportDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent.PatrimonialStatementVaeIndependentDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.vaeIndependent.SummaryAmount;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.task.*;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest", produces = {"application/media"})
public class PatrimonialStatementDtoController {

    private UUID idCreditRequestApplicant;
    private UUID idApplicant;
    private UUID idPatrimonialStatement;
    private String category;
    private String activity;
    private String product;
    private Integer numberRequest;
    private String typeClient;
    private Integer frecuency;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryParameter repositoryParameter;

    @Autowired
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;


    @GetMapping(value = "/v1/patrimonialStatementSalesReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  @ResponseBody byte[] getPatrimonialStatementSalesReport(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->{
            if(key.equals("id-credit-request-applicant")) idCreditRequestApplicant = UUID.fromString(value);
            if(key.equals("category")) category = value;
            if(key.equals("activity")) activity = value;
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();
        String jsonSalesHistory = patrimonialStatement.getFieldText2();
        ObjectMapper mapper = new ObjectMapper();
        List<SalesHistory> salesHistory = mapper.readValue(jsonSalesHistory,new TypeReference<List<SalesHistory>>(){});

        CreatePatrimonialStatementSales createPatrimonialStatementSales = new CreatePatrimonialStatementSales();

        PatrimonialStatementSalesHistoryDto patrimonialStatementSalesHistoryDto = createPatrimonialStatementSales.generateSales(applicant,salesHistory);

        InputStream stream = getClass().getResourceAsStream("/template-report/patrimonialStatementSales/sales.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();

//        String pathSubreport = getClass().getResource("/template-report/patrimonialStatementSales/").getPath();
//        String pathSubreport = resourceLoader.getResource("classpath:template-report/patrimonialStatementSales/").getURI().getPath();
        String pathSubreport = "template-report/patrimonialStatementSales/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);



        Collection<PatrimonialStatementSalesHistoryDto> collection = new ArrayList<>();
        collection.add(patrimonialStatementSalesHistoryDto);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    @GetMapping(value = "/v1/patrimonialStatementVaeIndependentReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  @ResponseBody byte[] getPatrimonialStatementVaeIndependentReport(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();

        CreatePatrimonialStatementVaeIndependent create = new CreatePatrimonialStatementVaeIndependent();
        PatrimonialStatementVaeIndependentDto pvae = create.generateVaeIndependent(applicant,patrimonialStatement);

        CreditRequestApplicant creditRequestApplicant = repositoryCreditRequestApplicant.getCreditRequestApplicantbyId(patrimonialStatement.getIdCreditRequestApplicant()).get();
        pvae.setNumberRequest(creditRequestApplicant.getNumberRequest());

        InputStream stream = getClass().getResourceAsStream("/template-report/patrimonialStatementVaeIndependent/vaeIndependent.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();

//        String pathSubreport = getClass().getResource("template-report/patrimonialStatementVaeIndependent/").getPath();
        String pathSubreport = "template-report/patrimonialStatementVaeIndependent/";

        frecuency = pvae.getFrecuency();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);
        params.put("frecuency",frecuency);

        Collection<PatrimonialStatementVaeIndependentDto> collection = new ArrayList<>();
        collection.add(pvae);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    @GetMapping(value="/v1/getOperativeEarningVaeIndependent")
    public @ResponseBody String getOperativeEarningVaeIndependent(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();

        CreatePatrimonialStatementVaeIndependent create = new CreatePatrimonialStatementVaeIndependent();
        PatrimonialStatementVaeIndependentDto pvae = create.generateVaeIndependent(applicant,patrimonialStatement);
        List<SummaryAmount>  listEarningExpenses = pvae.getListEarningExpenses();
        List<SummaryAmount> listEarningExpensesMub = pvae.getListEarningExpensesMub();

        if(listEarningExpenses!=null && listEarningExpensesMub!=null) {
            Double operativeUtility = listEarningExpenses.stream()
                    .filter(p -> p.getName().equals("Utilidad Operativa"))
                    .map(b -> b.getAmount())
                    .reduce(0.0, Double::sum);

            Double utilityMUB = listEarningExpensesMub.stream()
                    .filter(p -> p.getName().equals("Utilidad Operativa segun MUB"))
                    .map(b -> b.getAmount())
                    .reduce(0.0, Double::sum);
            List<Double> earnings = new ArrayList<>();
            earnings.add(operativeUtility);
            earnings.add(utilityMUB);
            Double earning =0.0;
            if(utilityMUB.doubleValue()<=0.0){
                earning = operativeUtility;
            }else {
                earning = earnings.stream().min(Comparator.naturalOrder()).get();
            }
            return earning.toString();
        }else{
            return "0";
        }
    }

    @GetMapping(value = "/v1/costProductReport",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getCostProductReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {

        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
            if(key.equals("product")) product = value;
        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();
        CreatePatrimonialStatementCostProduct createPatrimonialStatementCostProduct = new CreatePatrimonialStatementCostProduct();
        PatrimonialStatementCostProducts pcp = createPatrimonialStatementCostProduct.generateCostProduct(product, applicant,patrimonialStatement);
        InputStream stream = getClass().getResourceAsStream("/template-report/patrimonialStatementCostProduct/cost_product.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
//        String pathSubreport = getClass().getResource("/template-report/patrimonialStatementCostProduct/").getPath();
//        String pathSubreport = getClass().getClassLoader().getResource("template-report/patrimonialStatementCostProduct/").getPath();

        String pathSubreport = "template-report/patrimonialStatementCostProduct/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<PatrimonialStatementCostProducts> collection = new ArrayList<>();
        collection.add(pcp);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    @GetMapping(value = "/v1/productionInventoryReport",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getProductionInventoryReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();
        CreatePatrimonialStatementProductionInventory createPatrimonialStatementProductionInventory = new CreatePatrimonialStatementProductionInventory();
        PatrimonialStatementProductionInventory ppi = createPatrimonialStatementProductionInventory.generateProductionInventory(applicant,patrimonialStatement);
        InputStream stream = getClass().getResourceAsStream("/template-report/inventoryProduction/inventoryProduction.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
//        String pathSubreport = getClass().getResource("/template-report/inventoryProduction/").getPath();

//        String pathSubreport = getClass().getClassLoader().getResource("template-report/inventoryProduction/").getPath();
        String pathSubreport = "template-report/inventoryProduction/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<PatrimonialStatementProductionInventory> collection = new ArrayList<>();
        collection.add(ppi);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    @GetMapping(value = "/v1/salesInventoryReport",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getSalesInventoryReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
        });
        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();
        CreatePatrimonialStatementSalesInventory createPatrimonialStatementSalesInventory = new CreatePatrimonialStatementSalesInventory();
        PatrimonialStatementSalesInventory psi = createPatrimonialStatementSalesInventory.generateSalesInventory(applicant,patrimonialStatement);
        InputStream stream = getClass().getResourceAsStream("/template-report/inventorySales/inventorySales.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
//        String pathSubreport = getClass().getResource("/template-report/inventorySales/").getPath();

//        String pathSubreport =  getClass().getClassLoader().getResource("template-report/inventorySales/").getPath();
        String pathSubreport ="template-report/inventorySales/";

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<PatrimonialStatementSalesInventory> collection = new ArrayList<>();
        collection.add(psi);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }


    @GetMapping(value = "/v1/vaeDependentReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getVaeDependentReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
//            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
            if(key.equals("id-creditrequest-applicant")) idCreditRequestApplicant = UUID.fromString(value);
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);

        });

        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(idCreditRequestApplicant);
        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);

        List<SalaryAnalisys> salaryAnalisys = getAllSalayAnalysis(patrimonialStatementList);

        List<Parameter> parameterList = repositoryParameter.getParametersByCategory("VARIABLES VAE-DEPENDIENTE");
        patrimonialStatementList = patrimonialStatementList.stream()
                .filter(p -> p.getCategory().equals("EGRESOS"))
                .collect(Collectors.toList());
        
        VaeDependentReportDto vaeDependentReportDto = CreateVaeDependent.generate(salaryAnalisys,patrimonialStatementList,parameterList,applicant,paymentPlanList);
        vaeDependentReportDto.setNumberRequest(numberRequest);

        InputStream stream = getClass().getResourceAsStream("/template-report/patrimonialStatementVaeDependent/vaeDependent.jrxml");

        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String pathSubreport ="template-report/patrimonialStatementVaeDependent/";



        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<VaeDependentReportDto> collection= new ArrayList<>();
        collection.add(vaeDependentReportDto);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

    @GetMapping(value = "/v1/salaryVaeDependent")
    public @ResponseBody String getSalaryVaeDependent(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-patrimonial-statement")) idPatrimonialStatement = UUID.fromString(value);
            if(key.equals("type-client")) typeClient = value;
        });
        ObjectMapper mapper = new ObjectMapper();
        PatrimonialStatement patrimonialStatement = repositoryPatrimonialStatement.getById(idPatrimonialStatement).get();
        List<SalaryAnalisys> salaryAnalisys = mapper.readValue(patrimonialStatement.getFieldText2(), new TypeReference<List<SalaryAnalisys>>(){});

        Double aux = CreateVaeDependent.getSalaryDtoByMonth(salaryAnalisys,typeClient).getMountlyAverage();

        return aux.toString();
    }

    private List<SalaryAnalisys> getAllSalayAnalysis(List<PatrimonialStatement> patrimonialStatementList) throws IOException {
        List<SalaryAnalisys> salaryAnalisys = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        List<PatrimonialStatement> patrimonialStamentSalary = patrimonialStatementList.stream()
                .filter(p -> p.getElementCategory().equals("SUELDOS-HONORARIOS")).collect(Collectors.toList());

        for(PatrimonialStatement p:patrimonialStamentSalary){
            List<SalaryAnalisys> aux = mapper.readValue(p.getFieldText2(),new TypeReference<List<SalaryAnalisys>>(){});
            salaryAnalisys.addAll(aux);
        }

        return salaryAnalisys;
    }


}
