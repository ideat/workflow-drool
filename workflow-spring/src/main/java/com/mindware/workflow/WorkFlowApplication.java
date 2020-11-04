package com.mindware.workflow;

import com.mindware.workflow.spring.config.VerifyRolOptions;
import com.mindware.workflow.util.GenerateContract;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class WorkFlowApplication {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean
    public DataSource dataSource() {
        return dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @PostConstruct
    public void postConstruct() throws IOException {
//        iniciarEsquema(dataSource());

    }

    //Métodos privados
    private void iniciarEsquema(DataSource datasource) {
        ResourceDatabasePopulator inicializadorDb = new ResourceDatabasePopulator();
        inicializadorDb.addScript(new ClassPathResource("createSchema.sql"));

        try ( Connection dbConnection = datasource.getConnection() ) {

            inicializadorDb.populate(dbConnection);

        } catch (SQLException e) {
            //
        }
    }



    //Solo para Intellij
    @SneakyThrows
    public static void main(String[] args) {
//        SpringApplication.run(WorkFlowApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(WorkFlowApplication.class, args);

        context.getBean(VerifyRolOptions.class).updateOptionsRol();

//        VerifyRolOptions verifyRolOptions = new VerifyRolOptions();
//        try {
//            verifyRolOptions.updateOptionsRol();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
