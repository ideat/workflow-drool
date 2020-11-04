package com.mindware.workflow.spring.rest.sworeStatement;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.RepositoryStatementApplicants;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.StatementApplicants;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.ApplicantForStatementDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.PatrimonialStatementApplicantDto;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.RepositoryApplicantForStatementDto;
import com.mindware.workflow.core.service.task.CreatePatrimonialStatementApplicant;
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
public class SworeStatementController {
    private UUID idApplicant;
    private UUID idCreditRequestApplicant;
    private Integer numberRequest;
    private String origin;
    private String typeRelation;

    @Autowired
    RepositoryStatementApplicants repositoryStatementApplicants;

    @Autowired
    RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryApplicantForStatementDto repositoryApplicantForStatementDto;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryParameter repositoryParameter;

    @Autowired
    RepositoryTypeCredit repositoryTypeCredit;

    @GetMapping(value ="/v1/sworeStatementReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getSworeStatementReportByNumberRequest(@RequestHeader Map<String,String> headers) throws JRException, IOException {

        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-credit-request-applicant")) idCreditRequestApplicant = UUID.fromString(value);
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("origin-report")) origin = value;
            if(key.equals("type-relation")) typeRelation = value;
        });

        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        List<StatementApplicants> statementApplicants = repositoryStatementApplicants
                .getTotalStatementApplicantsByNumberRequestAndGuarantee(numberRequest);
        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement
                .getByIdCreditRequestApplicant(idCreditRequestApplicant);

        List<ApplicantForStatementDto> applicantForStatementDtoList = new ArrayList<>();
        if(typeRelation.equals("garante") || typeRelation.equals("codeudor")){
            applicantForStatementDtoList = repositoryApplicantForStatementDto
                    .getByNumberRequestNumberApplicantSpouseForGuarantorAndCodebtor(numberRequest,applicant.getNumberApplicant(), applicant.getNumberApplicantSpouse());
        }else {
            applicantForStatementDtoList = repositoryApplicantForStatementDto
                    .getByNumberRequestNumberApplicantSpouse(numberRequest, applicant.getNumberApplicantSpouse());
            applicantForStatementDtoList = completeDataApplicantForStatementDto(applicantForStatementDtoList);
        }

//        Parameter parameter = repositoryParameter.getParameterByCategoryAndValue("TIPO CREDITO",creditRequest.getTypeCredit()).get();
        TypeCredit typeCredit = repositoryTypeCredit.getByExternalCode(creditRequest.getTypeCredit()).get();

        CreatePatrimonialStatementApplicant createPatrimonialStatementApplicant = new CreatePatrimonialStatementApplicant();
        PatrimonialStatementApplicantDto psadto = createPatrimonialStatementApplicant
                .generatePatrimonialStatementApplicantDto(creditRequest,statementApplicants
        ,applicant, patrimonialStatementList,applicantForStatementDtoList,typeCredit.getDescription(),typeRelation);
        InputStream stream = null;
        if(origin.equals("Solicitud Credito")) {
            stream = getClass().getResourceAsStream("/template-report/applicantStatement/patrimonialStatementApplicant.jrxml");
        }else if(origin.equals("Solicitud Credito Persona Juridica")) {
            stream = getClass().getResourceAsStream("/template-report/applicantStatement/patrimonialStatementLegalApplicant.jrxml");
        }else {
            if(typeRelation.equals("deudor")) {
                stream = getClass().getResourceAsStream("/template-report/applicantStatement/swormStatement.jrxml");
            }else if(typeRelation.equals("codeudor")){
                stream = getClass().getResourceAsStream("/template-report/applicantStatement/swormStatementCodebtor.jrxml");
            }else if(typeRelation.equals("garante")){
                stream = getClass().getResourceAsStream("/template-report/applicantStatement/swormStatementGuarantor.jrxml");
            }
        }
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String pathSubreport = "template-report/applicantStatement/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<PatrimonialStatementApplicantDto> collection = new ArrayList<>();
        collection.add(psadto);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

    @GetMapping(value ="/v1/homeVerification", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getHomeVerificationReport(@RequestHeader Map<String,String> headers) throws JRException, IOException {
        headers.forEach((key,value) ->{
            if(key.equals("id-applicant")) idApplicant = UUID.fromString(value);
            if(key.equals("id-credit-request-applicant")) idCreditRequestApplicant = UUID.fromString(value);
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("type-relation")) typeRelation = value;
        });
        Applicant applicant = repositoryApplicant.getApplicantById(idApplicant).get();
        List<ApplicantForStatementDto> collection = new ArrayList<>();

        if(typeRelation.equals("deudor")) {
            collection = repositoryApplicantForStatementDto
                    .getByDataHomeVerificationApplicantSpouse(numberRequest, applicant.getNumberApplicant(),
                            applicant.getNumberApplicantSpouse(), typeRelation);
        }else{
            collection = repositoryApplicantForStatementDto
                    .getByDataHomeVerificationApplicantSpouseGuarantorCodebtor(numberRequest, applicant.getNumberApplicant(),
                            applicant.getNumberApplicantSpouse(), typeRelation);
        }

        InputStream stream = getClass().getResourceAsStream("/template-report/applicantStatement/homeVerification.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String pathSubreport ="template-report/applicantStatement/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        byte[] b = PrinterReportJasper.printCustomFile(stream,collection,params,"xls");
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);
    }


    private List<ApplicantForStatementDto> completeDataApplicantForStatementDto(List<ApplicantForStatementDto> applicantForStatementDtos){
        List<ApplicantForStatementDto> applicantForStatementDtoList = new ArrayList<>();

        for(ApplicantForStatementDto ap : applicantForStatementDtos){
            if(ap.getTypeApplicant().equals("4garante") && ap.getNumberApplicantSpouse()>0){
                Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(ap.getNumberApplicantSpouse()).get();
                ap.setNamesSpouse((applicant.getFirstName() + " " + applicant.getSecondName()).trim());
            }
            applicantForStatementDtoList.add(ap);
        }

        return applicantForStatementDtoList;
    }
}
