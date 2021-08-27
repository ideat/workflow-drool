package com.mindware.workflow.spring.config;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.entity.comercial.client.Client;
import com.mindware.workflow.core.entity.config.*;
import com.mindware.workflow.core.entity.contract.Contract;
import com.mindware.workflow.core.entity.contract.TemplateContract;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.entity.email.Mail;
import com.mindware.workflow.core.entity.exceptions.Authorizer;
import com.mindware.workflow.core.entity.exceptions.Exceptions;
import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import com.mindware.workflow.core.entity.contract.ContractVariable;
import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.rol.Rol;
import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import com.mindware.workflow.core.entity.workupReview.WorkUpReview;
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
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.service.data.exceptions.*;
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
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseFactory;
import com.mindware.workflow.core.usecase.applicant.CreateApplicant;
import com.mindware.workflow.core.usecase.cashFlow.CreateCashFlow;
import com.mindware.workflow.core.usecase.cityProvince.CreateCityProvince;
import com.mindware.workflow.core.usecase.comercial.client.CreateClient;
import com.mindware.workflow.core.usecase.config.*;
import com.mindware.workflow.core.usecase.contract.CreateContract;
import com.mindware.workflow.core.usecase.contract.CreateTemplateContract;
import com.mindware.workflow.core.usecase.creditRequest.CreateCreditRequest;
import com.mindware.workflow.core.usecase.creditRequestApplicant.CreateCreditRequestApplicant;
import com.mindware.workflow.core.usecase.creditResolution.CreateCreditResolution;
import com.mindware.workflow.core.usecase.email.CreateMail;
import com.mindware.workflow.core.usecase.exceptions.CreateAuthorizer;
import com.mindware.workflow.core.usecase.exceptions.CreateExceptions;
import com.mindware.workflow.core.usecase.exceptions.CreateExceptionsCreditRequest;
import com.mindware.workflow.core.usecase.legal.CreateContractVariable;
import com.mindware.workflow.core.usecase.legal.CreateLegalInformation;
import com.mindware.workflow.core.usecase.observation.CreateObservation;
import com.mindware.workflow.core.usecase.office.CreateOffice;
import com.mindware.workflow.core.usecase.patrimonialStatement.CreatePatrimonialStatement;
import com.mindware.workflow.core.usecase.rol.CreateRol;
import com.mindware.workflow.core.usecase.stageHistory.CreateStageHistory;
import com.mindware.workflow.core.usecase.templateObservation.CreateTemplateObservation;
import com.mindware.workflow.core.usecase.users.CreateUser;
import com.mindware.workflow.core.usecase.workUpReview.CreateWorkUpReview;

public class ServiceUseCaseFactory implements UseCaseFactory {
    private RepositoryOffice repositoryOffice;
    private RepositoryParameter repositoryParameter;
    private RepositoryProduct repositoryProduct;
    private RepositoryCompany repositoryCompany;
    private RepositoryCaedec repositoryCaedec;
    private RepositoryUsers repositoryUsers;
    private RepositoryApplicant repositoryApplicant;
    private RepositoryCreditRequest repositoryCreditRequest;
    private RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto;
    private RepositoryCreditRequestApplicant repositoryCreditRequestApplicant;
    private RepositoryTemplateForms repositoryTemplateForms;
    private RepositoryPatrimonialStatement repositoryPatrimonialStatement;
    private RepositoryPaymentPlan repositoryPaymentPlan;
    private RepositoryPaymentPlanDto repositoryPaymentPlanDto;
    private RepositoryWorkUpReview repositoryWorkUpReview;
    private RepositoryStatementApplicants repositoryStatementApplicants;
    private RepositoryApplicantForStatementDto repositoryApplicantForStatementDto;
    private RepositoryTemplateObservation repositoryTemplateObservation;
    private RepositoryObservation repositoryObservation;
    private RepositoryObservationCreditRequestApplicant repositoryObservationCreditRequestApplicant;
    private RepositoryCreditResolution repositoryCreditResolution;
    private RepositoryCreditResolutionCreditRequestDto repositoryCreditResolutionCreditRequestDto;
    private RepositoryLegalInformation repositoryLegalInformation;
    private RepositoryLegalInformationCreditRequestDto repositoryLegalInformationCreditRequestDto;
    private RepositoryRol repositoryRol;
    private RepositoryWorkflowProduct repositoryWorkflowProduct;
    private RepositoryStageHistory repositoryStageHistory;
    private RepositoryStageHistoryCreditRequestDto repositoryStageHistoryCreditRequestDto;
    private RepositoryMail repositoryMail;
    private RepositoryTemplateContract repositoryTemplateContract;
    private RepositoryCashFlow repositoryCashFlow;
    private RepositoryCashFlowCreditRequestApplicantDto repositoryCashFlowCreditRequestApplicantDto;
    private RepositoryExceptions repositoryExceptions;
    private RepositoryAuthorizer repositoryAuthorizer;
    private RepositoryUserAuthorizer repositoryUserAuthorizer;
    private RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest;
    private RepositoryExceptionsCreditRequestDto repositoryExceptionsCreditRequestDto;
    private RepositoryAuthorizerExceptionCreditRequestDto repositoryAuthorizerExceptionCreditRequestDto;
    private RepositoryUsersOfficeDto repositoryUsersOfficeDto;
    private RepositoryAuthorizerOfficeUserDto repositoryAuthorizerOfficeUserDto;
    private RepositoryAuthorizationExceptionReportDto repositoryAuthorizationExceptionReportDto;
    private RepositoryClient repositoryClient;
    private RepositoryExchangeRate repositoryExchangeRate;
    private RepositoryContractVariable repositoryContractVariable;
    private RepositoryContract repositoryContract;
    private RepositoryContractCreditRequestDto repositoryContractCreditRequestDto;
    private RepositoryCityProvince repositoryCityProvince;
    private RepositoryCreditRequestCompanySizeIndicatorDto repositoryCreditRequestCompanySizeIndicatorDto;
    private RepositoryTypeCredit repositoryTypeCredit;
    private RepositoryExceptionsApplicantCreditRequestDto repositoryExceptionsApplicantCreditRequestDto;
    private RepositoryStagePercentageDto repositoryStagePercentageDto;
    private RepositorySummaryCreditRequestStage repositorySummaryCreditRequestStage;

    @SuppressWarnings("rawtypes")
    @Override
    public UseCase create(String useCase, Object input) {
        switch (useCase){
            case "CreateOffice":
                return CreateOffice.create(this.repositoryOffice, (Office) input);
            case "CreateParameter":
                return CreateParameter.create(this.repositoryParameter,(Parameter) input);
            case "CreateProduct":
                return CreateProduct.create(this.repositoryProduct,(Product) input);
            case "CreateCompany":
                return CreateCompany.create(this.repositoryCompany, (Company) input);
            case "CreateCaedec":
                return CreateCaedec.create(this.repositoryCaedec, (Caedec)input);
            case "CreateUser":
                return CreateUser.create(this.repositoryUsers, (Users) input);
            case "CreateApplicant":
                return CreateApplicant.create(this.repositoryApplicant, (Applicant) input);
            case "CreateCreditRequest":
                return CreateCreditRequest.create(this.repositoryCreditRequest, (CreditRequest) input);
            case "CreateCreditRequestApplicant":
                return CreateCreditRequestApplicant.create(this.repositoryCreditRequestApplicant, (CreditRequestApplicant) input);
            case "CreateTemplateForms":
                return CreateTemplateForms.create(this.repositoryTemplateForms, (TemplateForm) input);
            case "CreatePatrimonialStatement":
                return CreatePatrimonialStatement.create(this.repositoryPatrimonialStatement, (PatrimonialStatement) input);
            case "CreateWorkUpReview":
                return CreateWorkUpReview.create(this.repositoryWorkUpReview,(WorkUpReview) input);
            case "CreateTemplateObservation":
                return CreateTemplateObservation.create(this.repositoryTemplateObservation,(TemplateObservation) input);
            case "CreateObservation":
                return CreateObservation.create(this.repositoryObservation,(Observation) input);
            case "CreateCreditResolution":
                return CreateCreditResolution.create(this.repositoryCreditResolution,(CreditResolution)input);
            case "CreateLegalInformation":
                return CreateLegalInformation.create(this.repositoryLegalInformation,(LegalInformation)input);
            case "CreateRol":
                return CreateRol.create(this.repositoryRol,(Rol) input);
            case "CreateWorkflowProduct":
                return CreateWorkflowProduct.create(this.repositoryWorkflowProduct,(WorkflowProduct)input);
            case "CreateStageHistory":
                return CreateStageHistory.create(this.repositoryStageHistory,(StageHistory)input);
            case "CreateMail":
                return CreateMail.create(this.repositoryMail,(Mail)input);
            case "CreateTemplateContract":
                return CreateTemplateContract.create(this.repositoryTemplateContract,(TemplateContract)input);
            case "CreateCashFlow":
                return CreateCashFlow.create(this.repositoryCashFlow,(CashFlow)input);
            case "CreateExceptions":
                return CreateExceptions.create(this.repositoryExceptions,(Exceptions) input);
            case "CreateAuthorizer":
                return CreateAuthorizer.create(this.repositoryAuthorizer,(Authorizer)input);
            case "CreateExceptionsCreditRequest":
                return CreateExceptionsCreditRequest.create(this.repositoryExceptionsCreditRequest,(ExceptionsCreditRequest) input);
            case "CreateClient":
                return CreateClient.create(this.repositoryClient,(Client) input);
            case "CreateExchangeRate":
                return CreateExchangeRate.create(this.repositoryExchangeRate,(ExchangeRate)input);
            case "CreateContractVariable":
                return CreateContractVariable.create(this.repositoryContractVariable,(ContractVariable) input);
            case "CreateContract":
                return CreateContract.create(this.repositoryContract,(Contract)input);
            case "CreateCityProvince":
                return CreateCityProvince.create(this.repositoryCityProvince,(CityProvince) input);
            case "CreateTypeCredit":
                return CreateTypeCredit.create(this.repositoryTypeCredit,(TypeCredit) input);

            default:
                throw new IllegalArgumentException(String.format("Caso de Uso '%s' desconocido.", useCase));
        }

    }

    public void setRepositoryOffice(RepositoryOffice repositoryOffice){
        this.repositoryOffice = repositoryOffice;
    }
    public void setRepositoryParameter(RepositoryParameter repositoryParameter){
        this.repositoryParameter = repositoryParameter;
    }
    public void setRepositoryProduct(RepositoryProduct repositoryProduct){
        this.repositoryProduct = repositoryProduct;
    }
    public void setRepositoryCompany(RepositoryCompany repositoryCompany){
        this.repositoryCompany = repositoryCompany;
    }

    public void setRepositoryCaedec(RepositoryCaedec repositoryCaedec){
        this.repositoryCaedec = repositoryCaedec;
    }

    public void setRepositoryUsers(RepositoryUsers repositoryUsers){
        this.repositoryUsers = repositoryUsers;
    }

    public void setRepositoryApplicant(RepositoryApplicant repositoryApplicant){
        this.repositoryApplicant = repositoryApplicant;
    }
    public void setRepositoryCreditRequest(RepositoryCreditRequest repositoryCreditRequest){
        this.repositoryCreditRequest = repositoryCreditRequest;
    }
    public void setRepositoryCreditRequestApplicantDto(RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto){
        this.repositoryCreditRequestApplicantDto = repositoryCreditRequestApplicantDto;
    }
    public void setRepositoryCreditRequestApplicant(RepositoryCreditRequestApplicant repositoryCreditRequestApplicant){
        this.repositoryCreditRequestApplicant = repositoryCreditRequestApplicant;
    }
    public void setRepositoryTemplateForms(RepositoryTemplateForms repositoryTemplateForms){
        this.repositoryTemplateForms = repositoryTemplateForms;
    }
    public void setRepositoryPatrimonialStatement(RepositoryPatrimonialStatement repositoryPatrimonialStatement){
        this.repositoryPatrimonialStatement = repositoryPatrimonialStatement;
    }
    public void setRepositoryPaymentPlan(RepositoryPaymentPlan repositoryPaymentPlan){
        this.repositoryPaymentPlan = repositoryPaymentPlan;
    }
    public void setRepositoryPaymentPlanDto(RepositoryPaymentPlanDto repositoryPaymentPlanDto){
        this.repositoryPaymentPlanDto = repositoryPaymentPlanDto;
    }
    public void setRepositoryWorkUpReview(RepositoryWorkUpReview repositoryWorkUpReview){
        this.repositoryWorkUpReview = repositoryWorkUpReview;
    }
    public void setRepositoryStatementApplicants(RepositoryStatementApplicants repositoryStatementApplicants){
        this.repositoryStatementApplicants = repositoryStatementApplicants;
    }
    public void setRepositoryApplicantForStatementDto(RepositoryApplicantForStatementDto repositoryApplicantForStatementDto){
        this.repositoryApplicantForStatementDto = repositoryApplicantForStatementDto;
    }
    public void setRepositoryTemplateObservation(RepositoryTemplateObservation repositoryTemplateObservation){
        this.repositoryTemplateObservation = repositoryTemplateObservation;
    }
    public void setRepositoryObservation(RepositoryObservation repositoryObservation){
        this.repositoryObservation = repositoryObservation;
    }
    public void setRepositoryObservationCreditRequestApplicant(RepositoryObservationCreditRequestApplicant repositoryObservationCreditRequestApplicant){
        this.repositoryObservationCreditRequestApplicant = repositoryObservationCreditRequestApplicant;
    }
    public void setRepositoryCreditResolution(RepositoryCreditResolution repositoryCreditResolution){
        this.repositoryCreditResolution = repositoryCreditResolution;
    }
    public void setRepositoryCreditResolutionCreditRequestDto(RepositoryCreditResolutionCreditRequestDto repositoryCreditResolutionCreditRequestDto){
        this.repositoryCreditResolutionCreditRequestDto = repositoryCreditResolutionCreditRequestDto;
    }
    public void setRepositoryLegalInformation(RepositoryLegalInformation repositoryLegalInformation){
        this.repositoryLegalInformation = repositoryLegalInformation;
    }
    public void setRepositoryLegalInformationCreditRequestDto(RepositoryLegalInformationCreditRequestDto repositoryLegalInformationCreditRequestDto){
        this.repositoryLegalInformationCreditRequestDto = repositoryLegalInformationCreditRequestDto;
    }
    public void setRepositoryRol(RepositoryRol repositoryRol){
        this.repositoryRol = repositoryRol;
    }
    public void setRepositoryWorkflowProduct(RepositoryWorkflowProduct repositoryWorkflowProduct){
        this.repositoryWorkflowProduct = repositoryWorkflowProduct;
    }
    public void setRepositoryStageHistory(RepositoryStageHistory repositoryStageHistory){
        this.repositoryStageHistory = repositoryStageHistory;
    }
    public void setRepositoryStageHistoryCreditRequestDto(RepositoryStageHistoryCreditRequestDto repositoryStageHistoryCreditRequestDto){
        this.repositoryStageHistoryCreditRequestDto = repositoryStageHistoryCreditRequestDto;
    }
    public void setRepositoryMail(RepositoryMail repositoryMail){
        this.repositoryMail = repositoryMail;
    }
    public void setRepositoryTemplateContract(RepositoryTemplateContract repositoryTemplateContract){
        this.repositoryTemplateContract = repositoryTemplateContract;
    }
    public void setRepositoryCashFlow(RepositoryCashFlow repositoryCashFlow){
        this.repositoryCashFlow = repositoryCashFlow;
    }
    public void setRepositoryCashFlowCreditRequestApplicantDto(RepositoryCashFlowCreditRequestApplicantDto repositoryCashFlowCreditRequestApplicantDto){
        this.repositoryStageHistoryCreditRequestDto = repositoryStageHistoryCreditRequestDto;
    }
    public void setRepositoryExceptions(RepositoryExceptions repositoryExceptions){
        this.repositoryExceptions = repositoryExceptions;
    }
    public void setRepositoryAuthorizer(RepositoryAuthorizer repositoryAuthorizer){
        this.repositoryAuthorizer = repositoryAuthorizer;
    }
    public void setRepositoryUserAuthorizer(RepositoryUserAuthorizer repositoryUserAuthorizer){
        this.repositoryAuthorizer = repositoryAuthorizer;
    }
    public void setRepositoryExceptionsCreditRequest(RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest){
        this.repositoryExceptionsCreditRequest = repositoryExceptionsCreditRequest;
    }
    public void setRepositoryExceptionsCreditRequestDto(RepositoryExceptionsCreditRequestDto repositoryExceptionsCreditRequestDto){
        this.repositoryExceptionsCreditRequestDto = repositoryExceptionsCreditRequestDto;
    }
    public void setRepositoryAuthorizerExceptionCreditRequestDto(RepositoryAuthorizerExceptionCreditRequestDto repositoryAuthorizerExceptionCreditRequestDto){
        this.repositoryAuthorizerExceptionCreditRequestDto = repositoryAuthorizerExceptionCreditRequestDto;
    }

    public void setRepositoryUsersOfficeDto(RepositoryUsersOfficeDto repositoryUsersOfficeDto){
        this.repositoryUsersOfficeDto = repositoryUsersOfficeDto;
    }

    public void setRepositoryAuthorizerOfficeUserDto(RepositoryAuthorizerOfficeUserDto repositoryAuthorizerOfficeUserDto){
        this.repositoryAuthorizerOfficeUserDto = repositoryAuthorizerOfficeUserDto;
    }

    public void setRepositoryAuthorizationExceptionReportDto(RepositoryAuthorizationExceptionReportDto repositoryAuthorizationExceptionReportDto){
        this.repositoryAuthorizationExceptionReportDto = repositoryAuthorizationExceptionReportDto;
    }

    public void setRepositoryClient(RepositoryClient repositoryClient){
        this.repositoryClient = repositoryClient;
    }

    public void setRepositoryExchangeRate(RepositoryExchangeRate repositoryExchangeRate){
        this.repositoryExchangeRate = repositoryExchangeRate;
    }

    public void setRepositoryContractVariable(RepositoryContractVariable repositoryContractVariable){
        this.repositoryContractVariable = repositoryContractVariable;
    }

    public void setRepositoryContract(RepositoryContract repositoryContract){
        this.repositoryContract = repositoryContract;
    }
    public void  setRepositoryContractCreditRequestDto(RepositoryContractCreditRequestDto repositoryContractCreditRequestDto){
        this.repositoryContractCreditRequestDto = repositoryContractCreditRequestDto;
    }
    public void setRepositoryCityProvince(RepositoryCityProvince repositoryCityProvince){
        this.repositoryCityProvince = repositoryCityProvince;
    }
    public void setRepositoryCreditRequestCompanySizeIndicatorDto(RepositoryCreditRequestCompanySizeIndicatorDto repositoryCreditRequestCompanySizeIndicatorDto){
        this.repositoryCreditRequestCompanySizeIndicatorDto = repositoryCreditRequestCompanySizeIndicatorDto;
    }
    public void setRepositoryTypeCredit(RepositoryTypeCredit repositoryTypeCredit){
        this.repositoryTypeCredit = repositoryTypeCredit;
    }

    public void setRepositoryExceptionsApplicantCreditRequestDto(RepositoryExceptionsApplicantCreditRequestDto repositoryExceptionsApplicantCreditRequestDto){
        this.repositoryExceptionsApplicantCreditRequestDto = repositoryExceptionsApplicantCreditRequestDto;
    }

    public void setRepositoryStagePercentageDto(RepositoryStagePercentageDto repositoryStagePercentageDto){
        this.repositoryStagePercentageDto = repositoryStagePercentageDto;
    }

    public void setRepositorySummaryCreditRequestStage(RepositorySummaryCreditRequestStage repositorySummaryCreditRequestStage){
        this.repositorySummaryCreditRequestStage = repositorySummaryCreditRequestStage;
    }
}
