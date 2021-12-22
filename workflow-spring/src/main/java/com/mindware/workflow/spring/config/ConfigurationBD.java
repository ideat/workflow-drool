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
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestApplicantDto;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestCompanySizeIndicatorDto;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.service.data.creditResolution.dto.RepositoryCreditResolutionCreditRequestDto;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringCreditRequest;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringProduct;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.service.data.exceptions.*;
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
import com.mindware.workflow.persistence.applicant.RepositoryApplicantMybatis;
import com.mindware.workflow.persistence.cashFlow.RepositoryCashFlowCreditRequestApplicantDtoMybatis;
import com.mindware.workflow.persistence.cashFlow.RepositoryCashFlowMybatis;
import com.mindware.workflow.persistence.comercial.client.RepositoryClientMybatis;
import com.mindware.workflow.persistence.config.*;
import com.mindware.workflow.persistence.contract.RepositoryContractCreditRequestDtoMybatis;
import com.mindware.workflow.persistence.contract.RepositoryContractMybatis;
import com.mindware.workflow.persistence.contract.RepositoryTemplateContractMybatis;
import com.mindware.workflow.persistence.creditRequestApplicantDto.RepositoryCreditRequestApplicantDtoMybatis;
import com.mindware.workflow.persistence.creditRequest.RepositoryCreditRequestMybatis;
import com.mindware.workflow.persistence.creditRequestApplicant.RepositoryCreditRequestApplicantMybatis;
import com.mindware.workflow.persistence.creditRequestCompanySizeIndicatorDto.RepositoryCreditRequestCompanySizeIndicatorDtoMybatis;
import com.mindware.workflow.persistence.creditResolution.RepositoryCreditResolutionMybatis;
import com.mindware.workflow.persistence.creditResolution.dto.RepositoryCreditResolutionCreditRequestDtoMybatis;
import com.mindware.workflow.persistence.creditScoring.RepositoryScoringCreditRequestMybatis;
import com.mindware.workflow.persistence.creditScoring.RepositoryScoringProductMybatis;
import com.mindware.workflow.persistence.email.RepositoryMailMybatis;
import com.mindware.workflow.persistence.exceptions.*;
import com.mindware.workflow.persistence.contract.RepositoryContractVariableMybatis;
import com.mindware.workflow.persistence.kiosco.RepositoryProductKioscoMybatis;
import com.mindware.workflow.persistence.kiosco.RepositorySummaryCreditRequestStageMybatis;
import com.mindware.workflow.persistence.legal.RepositoryLegalInformationMybatis;
import com.mindware.workflow.persistence.legal.dto.RepositoryLegalInformationCreditRequestDtoMybatis;
import com.mindware.workflow.persistence.observation.RepositoryObservationMybatis;
import com.mindware.workflow.persistence.observation.dto.RepositoryObservationCreditRequestApplicantMybatis;
import com.mindware.workflow.persistence.office.RepositoryOfficeMybatis;
import com.mindware.workflow.persistence.patrimonialStatement.RepositoryPatrimonialStatementMybatis;
import com.mindware.workflow.persistence.patrimonialStatement.statementApplicants.RepositoryStatementApplicantsMyBatis;
import com.mindware.workflow.persistence.patrimonialStatement.sworeStatement.RepositoryApplicantForStatementDtoMybatis;
import com.mindware.workflow.persistence.paymentPlan.RepositoryPaymentPlanMybatis;
import com.mindware.workflow.persistence.paymentPlan.report.RepositoryPaymentPlanDtoMybatis;
import com.mindware.workflow.persistence.rol.RepositoryRolMybatis;
import com.mindware.workflow.persistence.stadistic.RepositoryStagePercentageDtoMybatis;
import com.mindware.workflow.persistence.stageHistory.RepositoryStageHistoryMybatis;
import com.mindware.workflow.persistence.stageHistoryCreditRequestDto.RepositoryStageHistoryCreditRequestDtoMybatis;
import com.mindware.workflow.persistence.templateObservation.RepositoryTemplateObservationMybatis;
import com.mindware.workflow.persistence.users.RepositoryUsersMybatis;
import com.mindware.workflow.persistence.users.RepositoryUsersOfficeDtoMybatis;
import com.mindware.workflow.persistence.workUpReview.RepositoryWorkUpReviewMybatis;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class ConfigurationBD {
    private static final String ESQUEMA_SISTEMA = "workflow";

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    DataSource datasource;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Bean
    public RepositoryOffice repositoryOffice() {
        return RepositoryOfficeMybatis.create(sqlSessionFactory);
    }

    @Bean
    public RepositoryParameter repositoryParameter(){
        return RepositoryParameterMybatis.create(sqlSessionFactory);
    }

    @Bean
    public RepositoryProduct repositoryProduct(){
        return RepositoryProductMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCompany repositoryCompany(){
        return RepositoryCompanyMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCaedec repositoryCaedec(){
        return RepositoryCaedecMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryUsers repositoryUsers(){
        return RepositoryUsersMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryApplicant repositoryApplicant(){
        return RepositoryApplicantMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditRequest repositoryCreditRequest(){
        return RepositoryCreditRequestMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditRequestApplicantDto repositoryCreditRequestApplicantDto(){
        return RepositoryCreditRequestApplicantDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditRequestApplicant repositoryCreditRequestApplicant(){
        return RepositoryCreditRequestApplicantMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryTemplateForms repositoryTemplateForms(){
        return RepositoryTemplateFormsMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryPatrimonialStatement repositoryPatrimonialStatement(){
        return RepositoryPatrimonialStatementMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryPaymentPlan repositoryPaymentPlan(){
        return RepositoryPaymentPlanMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryPaymentPlanDto repositoryPaymentPlanDto(){
        return RepositoryPaymentPlanDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryWorkUpReview repositoryWorkUpReview(){
        return RepositoryWorkUpReviewMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryStatementApplicants repositoryStatementApplicants(){
        return RepositoryStatementApplicantsMyBatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryApplicantForStatementDto repositoryApplicantForStatementDto(){
        return RepositoryApplicantForStatementDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryTemplateObservation repositoryTemplateObservation(){
        return RepositoryTemplateObservationMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryObservation repositoryObservation(){
        return RepositoryObservationMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryObservationCreditRequestApplicant repositoryObservationCreditRequestApplicant(){
        return RepositoryObservationCreditRequestApplicantMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditResolution repositoryCreditResolution(){
        return RepositoryCreditResolutionMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditResolutionCreditRequestDto repositoryCreditResolutionCreditRequestDto(){
        return RepositoryCreditResolutionCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryLegalInformation repositoryLegalInformation(){
        return RepositoryLegalInformationMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryLegalInformationCreditRequestDto repositoryLegalInformationCreditRequestDto(){
        return RepositoryLegalInformationCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryRol repositoryRol(){
        return RepositoryRolMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryWorkflowProduct repositoryWorkflowProduct(){
        return RepositoryWorkflowProductMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryStageHistory repositoryStageHistory(){
        return RepositoryStageHistoryMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryStageHistoryCreditRequestDto repositoryStageHistoryCreditRequestDto(){
        return RepositoryStageHistoryCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryMail repositoryMail(){
        return RepositoryMailMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryTemplateContract repositoryTemplateContract(){
        return RepositoryTemplateContractMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCashFlow repositoryCashFlow(){
        return RepositoryCashFlowMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCashFlowCreditRequestApplicantDto repositoryCashFlowCreditRequestApplicantDto(){
        return RepositoryCashFlowCreditRequestApplicantDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryExceptions repositoryExceptions(){
        return RepositoryExceptionsMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryAuthorizer repositoryAuthorizer(){
        return RepositoryAuthorizerMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryUserAuthorizer repositoryUserAuthorizer(){
        return RepositoryUserAuthorizerMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryExceptionsCreditRequest repositoryExceptionsCreditRequest(){
        return RepositoryExceptionsCreditRequestMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryExceptionsCreditRequestDto repositoryExceptionsCreditRequestDto(){
        return RepositoryExceptionsCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryAuthorizerExceptionCreditRequestDto repositoryAuthorizerExceptionCreditRequestDto(){
        return RepositoryAuthorizerExceptionCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryUsersOfficeDto repositoryUsersOfficeDto(){
        return RepositoryUsersOfficeDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryAuthorizerOfficeUserDto repositoryAuthorizerOfficeUserDto(){
        return RepositoryAuthorizerOfficeUserDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryAuthorizationExceptionReportDto repositoryAuthorizationExceptionReportDto(){
        return RepositoryAuthorizationExceptionReportDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryClient repositoryClient(){
        return RepositoryClientMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryExchangeRate repositoryExchangeRate(){
        return RepositoryExchangeRateMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryContractVariable repositoryContractVariable(){
        return RepositoryContractVariableMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryContract repositoryContract(){
        return RepositoryContractMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryContractCreditRequestDto repositoryContractCreditRequestDto() {
       return RepositoryContractCreditRequestDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCityProvince repositoryCityProvince(){
        return RepositoryCityProvinceMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryCreditRequestCompanySizeIndicatorDto repositoryCreditRequestCompanySizeIndicatorDto(){
        return RepositoryCreditRequestCompanySizeIndicatorDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryTypeCredit repositoryTypeCredit(){
        return RepositoryTypeCreditMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryExceptionsApplicantCreditRequestDto repositoryExceptionsApplicantCreditRequestDto(){
        return RepositoryExceptionsApplicantCreditRequestDtoMyBatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryStagePercentageDto repositoryStagePercentageDto(){
       return RepositoryStagePercentageDtoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositorySummaryCreditRequestStage repositorySummaryCreditRequestStage(){
        return RepositorySummaryCreditRequestStageMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryProductKiosco repositoryProductKiosco(){
        return RepositoryProductKioscoMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryScoringProduct repositoryScoringProduct(){
        return RepositoryScoringProductMybatis.create(sqlSessionFactory);
    }

    @Bean
    RepositoryScoringCreditRequest repositoryScoringCreditRequest(){
        return RepositoryScoringCreditRequestMybatis.create(sqlSessionFactory);
    }

    @PostConstruct
    public void postConstruct() {
        createSchema();
    }

    // Métodos privados
    private void createSchema() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        Resource[] resources = null;
        try {
           resources = resolver.getResources("classpath*:/*.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(resources);

        ResourceDatabasePopulator initDb = new ResourceDatabasePopulator();
        initDb.addScript(new ClassPathResource("createSchema.sql"));

        try (Connection dbConnection = datasource.getConnection()) {
            String selectSchemaSQL = String.format("SELECT count(*) > 0 schemaExists%n" +
                    "FROM information_schema.schemata%n" +
                    "WHERE schema_name = '%s'", ESQUEMA_SISTEMA);

            try (Statement statement = dbConnection.createStatement();
                 ResultSet rs = statement.executeQuery(selectSchemaSQL)) {
                rs.next();
                Boolean schemaExists = rs.getBoolean("schemaExists");

                if (!schemaExists) {
                    initDb.populate(dbConnection);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

