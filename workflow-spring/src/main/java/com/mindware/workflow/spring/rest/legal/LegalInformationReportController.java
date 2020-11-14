package com.mindware.workflow.spring.rest.legal;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import com.mindware.workflow.core.service.data.legal.dto.LegalInformationReportDto;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.service.data.users.RepositoryUsersOfficeDto;
import com.mindware.workflow.core.service.data.users.dto.UsersOfficeDto;
import com.mindware.workflow.core.service.task.CreateLegalInformationReportDto;
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
public class LegalInformationReportController {
    private Integer numberRequest;
    private Integer numberApplicant;
    private String createdBy;
    private String loginOfficer;

    @Autowired
    RepositoryApplicant repositoryApplicant;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryLegalInformation repositoryLegalInformation;

    @Autowired
    RepositoryUsersOfficeDto repositoryUsersOfficeDto;

    @GetMapping(value = "/v1/legalInformationReport",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getLegalInformationReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("number-request")) numberRequest = Integer.parseInt(value);
            if(key.equals("created-by")) createdBy = value;
            if(key.equals("number-applicant")) numberApplicant = Integer.parseInt(value);
        });

        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        LegalInformation legalInformation = repositoryLegalInformation.getByNumberRequest(numberRequest).get();
        Applicant applicant = repositoryApplicant.getApplicantByNumberApplicant(numberApplicant).get();
        Map<String,String> names = new HashMap<>();
        loginOfficer = creditRequest.getLoginUser();

        Users users = new Users();
        users = repositoryUsers.getUserByIdUser(loginOfficer).get();
        names.put("nameOfficer",users.getFullName());
        users = repositoryUsers.getUserByIdUser(createdBy).get();
        names.put("nameLegalAdviser",users.getFullName());

        UsersOfficeDto usersOfficeDto = repositoryUsersOfficeDto.getByRol("LEGAL_NACIONAL").get(0);
        names.put("legalAnalyst",usersOfficeDto.getFullName());

        LegalInformationReportDto legalInformationReportDto = CreateLegalInformationReportDto.generate(legalInformation,
                creditRequest,applicant,names);


        InputStream stream = getClass().getResourceAsStream("/template-report/legalInformation/legalInformation.jrxml");
        String pathLogo = getClass().getResource("/template-report/img/logo.png").getPath();
        String pathSubreport = "template-report/legalInformation/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);

        Collection<LegalInformationReportDto> collection = new ArrayList<>();
        collection.add(legalInformationReportDto);
        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);
        return IOUtils.toByteArray(is);

    }

}
