package com.mindware.workflow.spring.config;

import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlow;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlowCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.cityProvince.RepositoryCityProvince;
import com.mindware.workflow.core.service.data.comercial.client.RepositoryClient;
import com.mindware.workflow.core.service.data.config.*;
import com.mindware.workflow.core.service.data.contract.RepositoryContract;
import com.mindware.workflow.core.service.data.contract.RepositoryContractCreditRequestDto;
import com.mindware.workflow.core.service.data.contract.RepositoryTemplateContract;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabled;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabledApplicantDto;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringCreditRequest;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringProduct;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.service.data.exceptions.*;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsible;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleDto;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsibleReport;
import com.mindware.workflow.core.service.data.kiosco.RepositoryProductKiosco;
import com.mindware.workflow.core.service.data.kiosco.RepositorySummaryCreditRequestStage;
import com.mindware.workflow.core.service.data.legal.RepositoryContractVariable;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import com.mindware.workflow.core.service.data.legal.dto.RepositoryLegalInformationCreditRequestDto;
import com.mindware.workflow.core.service.data.observation.RepositoryObservation;
import com.mindware.workflow.core.service.data.observation.dto.RepositoryObservationCreditRequestApplicant;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.statementApplicants.RepositoryStatementApplicants;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.sworeStatement.RepositoryApplicantForStatementDto;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.data.paymentPlan.report.RepositoryPaymentPlanDto;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import com.mindware.workflow.core.service.data.stadistic.RepositoryStagePercentageDto;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistoryCreditRequestDto;
import com.mindware.workflow.core.service.data.templateObservation.RepositoryTemplateObservation;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.service.data.users.RepositoryUsersOfficeDto;
import com.mindware.workflow.core.service.data.workUpReview.RepositoryWorkUpReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(name = {"com.mindware.workflow.core.usecase.UseCaseFactory"})
@Import(ConfigurationBD.class)
public class AutoConfigurationWorkflow {


    @Autowired
    private RepositoryOffice repositoryOffice;

    @Autowired
    private RepositoryParameter repositoryParameter;

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Autowired
    private RepositoryCompany repositoryCompany;

    @Autowired
    private RepositoryCaedec repositoryCaedec;

    @Autowired
    private RepositoryUsers repositoryUsers;

    @Autowired
    private RepositoryApplicant repositoryApplicant;

    @Autowired
    private RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    private RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;

    @Autowired
    private RepositoryTemplateForms repositoryTemplateForms;

    @Autowired
    private RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    private RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    private RepositoryPaymentPlanDto repositoryPaymentPlanDto;

    @Autowired
    private RepositoryWorkUpReview repositoryWorkUpReview;

    @Autowired
    private RepositoryStatementApplicants repositoryStatementApplicants;

    @Autowired
    private RepositoryApplicantForStatementDto repositoryApplicantForStatementDto;

    @Autowired
    private RepositoryTemplateObservation repositoryTemplateObservation;

    @Autowired
    private RepositoryObservation repositoryObservation;

    @Autowired
    private RepositoryObservationCreditRequestApplicant repositoryObservationCreditRequestApplicant;

    @Autowired
    private RepositoryCreditResolution repositoryCreditResolution;

    @Autowired
    private RepositoryCreditResolutionCreditRequestDto repositoryCreditResolutionCreditRequestDto;

    @Autowired
    private RepositoryLegalInformation repositoryLegalInformation;

    @Autowired
    private RepositoryLegalInformationCreditRequestDto repositoryLegalInformationCreditRequestDto;

    @Autowired
    private RepositoryRol repositoryRol;

    @Autowired
    private RepositoryWorkflowProduct repositoryWorkflowProduct;

    @Autowired
    private RepositoryStageHistory repositoryStageHistory;

    @Autowired
    private RepositoryStageHistoryCreditRequestDto repositoryStageHistoryCreditRequestDto;

    @Autowired
    private RepositoryMail repositoryMail;

    @Autowired
    private RepositoryTemplateContract repositoryTemplateContract;

    @Autowired
    private RepositoryCashFlow repositoryCashFlow;

    @Autowired
    private RepositoryCashFlowCreditRequestApplicantDto repositoryCashFlowCreditRequestApplicantDto;

    @Autowired
    private RepositoryExceptions repositoryExceptions;

    @Autowired
    private RepositoryAuthorizer repositoryAuthorizer;

    @Autowired
    private RepositoryUserAuthorizer repositoryUserAuthorizer;

    @Autowired
    private RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest;

    @Autowired
    private RepositoryExceptionsCreditRequestDto repositoryExceptionsCreditRequestDto;

    @Autowired
    private RepositoryAuthorizerExceptionCreditRequestDto repositoryAuthorizerExceptionCreditRequestDto;

    @Autowired
    private RepositoryUsersOfficeDto repositoryUsersOfficeDto;

    @Autowired
    private RepositoryAuthorizerOfficeUserDto repositoryAuthorizerOfficeUserDto;

    @Autowired
    private RepositoryAuthorizationExceptionReportDto repositoryAuthorizationExceptionReportDto;

    @Autowired
    private RepositoryClient repositoryClient;

    @Autowired
    private RepositoryExchangeRate repositoryExchangeRate;

    @Autowired
    private RepositoryContractVariable repositoryContractVariable;

    @Autowired
    private RepositoryContract repositoryContract;

    @Autowired
    private RepositoryContractCreditRequestDto repositoryContractCreditRequestDto;

    @Autowired
    private RepositoryCityProvince repositoryCityProvince;

    @Autowired
    private RepositoryCreditRequestCompanySizeIndicatorDto repositoryCreditRequestCompanySizeIndicatorDto;

    @Autowired
    private RepositoryTypeCredit repositoryTypeCredit;

    @Autowired
    private RepositoryExceptionsApplicantCreditRequestDto repositoryExceptionsApplicantCreditRequestDto;

    @Autowired
    private RepositoryStagePercentageDto repositoryStagePercentageDto;

    @Autowired
    private RepositorySummaryCreditRequestStage repositorySummaryCreditRequestStage;

    @Autowired
    private RepositoryProductKiosco repositoryProductKiosco;

    @Autowired
    private RepositoryScoringProduct repositoryScoringProduct;

    @Autowired
    private RepositoryScoringCreditRequest repositoryScoringCreditRequest;

    @Autowired
    private RepositoryCreditRequestEnabled repositoryCreditRequestEnabled;

    @Autowired
    private RepositoryCreditRequestEnabledApplicantDto repositoryCreditRequestEnabledApplicantDto;

    @Autowired
    private RepositoryHistoryChangeResponsible repositoryHistoryChangeResponsible;

    @Autowired
    private RepositoryHistoryChangeResponsibleDto repositoryHistoryChangeResponsibleDto;

    @Autowired
    private RepositoryHistoryChangeResponsibleReport repositoryHistoryChangeResponsibleReport;

    @Bean
    @DependsOn({"repositoryOffice"})
    public ServiceUseCaseFactory serviceUseCaseFactory(){
        ServiceUseCaseFactory factory = new ServiceUseCaseFactory();

        factory.setRepositoryOffice(repositoryOffice);
        factory.setRepositoryParameter(repositoryParameter);
        factory.setRepositoryProduct(repositoryProduct);
        factory.setRepositoryCompany(repositoryCompany);
        factory.setRepositoryCaedec(repositoryCaedec);
        factory.setRepositoryUsers(repositoryUsers);
        factory.setRepositoryApplicant(repositoryApplicant);
        factory.setRepositoryCreditRequest(repositoryCreditRequest);
        factory.setRepositoryCreditRequestApplicant(repositoryCreditRequestApplicant);
        factory.setRepositoryTemplateForms(repositoryTemplateForms);
        factory.setRepositoryPatrimonialStatement(repositoryPatrimonialStatement);
        factory.setRepositoryPaymentPlan(repositoryPaymentPlan);
        factory.setRepositoryPaymentPlanDto(repositoryPaymentPlanDto);
        factory.setRepositoryWorkUpReview(repositoryWorkUpReview);
        factory.setRepositoryStatementApplicants(repositoryStatementApplicants);
        factory.setRepositoryApplicantForStatementDto(repositoryApplicantForStatementDto);
        factory.setRepositoryTemplateObservation(repositoryTemplateObservation);
        factory.setRepositoryObservation(repositoryObservation);
        factory.setRepositoryObservationCreditRequestApplicant(repositoryObservationCreditRequestApplicant);
        factory.setRepositoryCreditResolution(repositoryCreditResolution);
        factory.setRepositoryCreditResolutionCreditRequestDto(repositoryCreditResolutionCreditRequestDto);
        factory.setRepositoryLegalInformation(repositoryLegalInformation);
        factory.setRepositoryLegalInformationCreditRequestDto(repositoryLegalInformationCreditRequestDto);
        factory.setRepositoryRol(repositoryRol);
        factory.setRepositoryWorkflowProduct(repositoryWorkflowProduct);
        factory.setRepositoryStageHistory(repositoryStageHistory);
        factory.setRepositoryStageHistoryCreditRequestDto(repositoryStageHistoryCreditRequestDto);
        factory.setRepositoryMail(repositoryMail);
        factory.setRepositoryTemplateContract(repositoryTemplateContract);
        factory.setRepositoryCashFlow(repositoryCashFlow);
        factory.setRepositoryStageHistoryCreditRequestDto(repositoryStageHistoryCreditRequestDto);
        factory.setRepositoryCashFlowCreditRequestApplicantDto(repositoryCashFlowCreditRequestApplicantDto);
        factory.setRepositoryExceptions(repositoryExceptions);
        factory.setRepositoryAuthorizer(repositoryAuthorizer);
        factory.setRepositoryUserAuthorizer(repositoryUserAuthorizer);
        factory.setRepositoryExceptionsCreditRequest(repositoryExceptionsCreditRequest);
        factory.setRepositoryExceptionsCreditRequestDto(repositoryExceptionsCreditRequestDto);
        factory.setRepositoryAuthorizerExceptionCreditRequestDto(repositoryAuthorizerExceptionCreditRequestDto);
        factory.setRepositoryUsersOfficeDto(repositoryUsersOfficeDto);
        factory.setRepositoryAuthorizerOfficeUserDto(repositoryAuthorizerOfficeUserDto);
        factory.setRepositoryAuthorizationExceptionReportDto(repositoryAuthorizationExceptionReportDto);
        factory.setRepositoryClient(repositoryClient);
        factory.setRepositoryExchangeRate(repositoryExchangeRate);
        factory.setRepositoryContractVariable(repositoryContractVariable);
        factory.setRepositoryContract(repositoryContract);
        factory.setRepositoryContractCreditRequestDto(repositoryContractCreditRequestDto);
        factory.setRepositoryCityProvince(repositoryCityProvince);
        factory.setRepositoryCreditRequestCompanySizeIndicatorDto(repositoryCreditRequestCompanySizeIndicatorDto);
        factory.setRepositoryTypeCredit(repositoryTypeCredit);
        factory.setRepositoryExceptionsApplicantCreditRequestDto(repositoryExceptionsApplicantCreditRequestDto);
        factory.setRepositoryStagePercentageDto(repositoryStagePercentageDto);
        factory.setRepositorySummaryCreditRequestStage(repositorySummaryCreditRequestStage);
        factory.setRepositoryProductKiosco(repositoryProductKiosco);
        factory.setRepositoryScoringProduct(repositoryScoringProduct);
        factory.setRepositoryScoringCreditRequest(repositoryScoringCreditRequest);
        factory.setRepositoryCreditRequestEnabled(repositoryCreditRequestEnabled);
        factory.setRepositoryCreditRequestEnabledApplicantDto(repositoryCreditRequestEnabledApplicantDto);
        factory.setRepositoryHistoryChangeResponsible(repositoryHistoryChangeResponsible);
        factory.setRepositoryHistoryChangeResponsibleDto(repositoryHistoryChangeResponsibleDto);
        factory.setRepositoryHistoryChangeResponsibleReport(repositoryHistoryChangeResponsibleReport);
        return factory;
    }
}
