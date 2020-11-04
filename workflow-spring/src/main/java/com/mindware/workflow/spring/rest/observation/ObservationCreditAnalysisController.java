package com.mindware.workflow.spring.rest.observation;

import com.mindware.workflow.core.entity.*;
import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.service.data.config.dto.TypeCreditProductCreditDto;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.observation.RepositoryObservation;
import com.mindware.workflow.core.service.data.observation.dto.ObservationCreditRequestApplicant;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.service.task.CreateObservationCreditRequestApplicant;
import com.mindware.workflow.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ObservationCreditAnalysisController {
    private Integer numberApplicant;
    private Integer numberRequest;
    private String task;
//    private UUID idApplicant;
    private UUID idCreditRequestApplicant;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryParameter repositoryParameter;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryObservation repositoryObservation;

    @Autowired
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;

    @Autowired
    RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryOffice repositoryOffice;

    @Autowired
    RepositoryTypeCredit repositoryTypeCredit;

    @GetMapping(value = "/v1/observationCreditAnalysis", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getObservationCreditAnalysis(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->{
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("number-applicant")) numberApplicant = Integer.parseInt(value);
            if(key.equals("task")) task = value;
        });

        Map<String,String> mapData = new HashMap<>();

        Observation observation = repositoryObservation.getByNumberRequestApplicantTask(numberRequest,task).get();
        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(numberApplicant).get();
        CreditRequestApplicant creditRequestApplicant = repositoryCreditRequestApplicant.getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(numberRequest,numberApplicant,"deudor").get();
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(creditRequestApplicant.getId());
        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);
        Users users = repositoryUsers.getUserByIdUser(creditRequest.getLoginUser()).get();
        TypeCredit typeCredit = repositoryTypeCredit.getByExternalCode(creditRequest.getTypeCredit()).get();

        mapData.put("typeCredit",typeCredit.getDescription());
        Parameter parameter = repositoryParameter.getParameterByCategoryAndValue("CAEDEC",applicant.getCaedec()).get();
        mapData.put("mainActivity",parameter.getDescription());
        Office office = repositoryOffice.getOfficeByInternalCode(creditRequest.getIdOffice()).get();
        mapData.put("agency",office.getName());

        CreateObservationCreditRequestApplicant createObservationCreditRequestApplicant = new CreateObservationCreditRequestApplicant();

        ObservationCreditRequestApplicant result = createObservationCreditRequestApplicant.generate(observation,patrimonialStatementList,applicant,
                users,creditRequest,mapData,paymentPlanList);

        InputStream stream = null;
        stream = getClass().getResourceAsStream("/template-report/observation/observationCreditAnalysis.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String pathSubreport ="template-report/observation/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<ObservationCreditRequestApplicant> collection = new ArrayList<>();
        collection.add(result);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }

}
