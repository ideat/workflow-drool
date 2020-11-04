package com.mindware.workflow.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindware.workflow.core.usecase.UseCaseFactory;
import com.networknt.service.SingletonServiceFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class TestIntegrationBase {
    protected static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());

    }



    protected String objectToJsonString(Object obj) throws JsonProcessingException {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj );
    }

    protected JsonNode objectToJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    //Spring


    protected UseCaseFactory getCasoUsoFactory() {
        ConfigurableApplicationContext springContext = SingletonServiceFactory.getBean(ConfigurableApplicationContext.class);
        return springContext.getBean(UseCaseFactory.class);
    }


    protected DataSource getDataSource() {
        ConfigurableApplicationContext springContext = SingletonServiceFactory.getBean(ConfigurableApplicationContext.class);
        return springContext.getBean(DataSource.class);
    }

    protected void runBeforeTestSqlScript() throws SQLException {
        runSqlScript(this.getBeforeTestSqlScript());
    }

    protected void runAfterTestSqlScript() throws SQLException {
        runSqlScript(this.getAfterTestSqlScript());
    }


    private String getBeforeTestSqlScript() {
        return this.getClass().getSimpleName() + "Before.sql";
    }

    private String getAfterTestSqlScript() {
        return this.getClass().getSimpleName() + "After.sql";
    }

    private void runSqlScript(String sqlScriptResource) throws SQLException {
        ResourceDatabasePopulator inicializadorDb = new ResourceDatabasePopulator();
        inicializadorDb.addScript(new ClassPathResource( sqlScriptResource ));

        try ( Connection dbConnection = getDataSource().getConnection() ) {
            inicializadorDb.populate(dbConnection);
        }
    }
}
