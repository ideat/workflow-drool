package com.mindware.workflow.schedule;

import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Service
public class ConfigurationService {
    private static final Logger LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);

    @Autowired
    RepositoryParameter repositoryParameter;

    private Map<String, Parameter> listParameter;
    private List<String> mandatoryConfigs;

    public ConfigurationService(){
        listParameter = new WeakHashMap<>();
        mandatoryConfigs = new ArrayList<>();
        mandatoryConfigs.add(Constants.CONFIG_RESET_USER_PASSWORD);
        mandatoryConfigs.add(Constants.CONFIG_ALERT_EXPIRATION_EXCEPTIONS);
        mandatoryConfigs.add(Constants.CONFIG_ALERT_EXPIRATION_STATE_WORKFLOW);
    }

    @PostConstruct
    public void loadConfiguration(){
        LOGGER.debug("Scheduled Event: Configuration table loaded/updated from database");
        StringBuilder sb = new StringBuilder();
        sb.append("Configuration Parameters:");
        List<Parameter> configs = repositoryParameter.getParametersByCategory("PLANIFICACION-EJECUCION");
        for(Parameter configuration:configs){
            sb.append("\n" + configuration.getValue() + ":" + configuration.getDescription());
            listParameter.put(configuration.getValue(),configuration);
        }
    }

    public Parameter getConfiguration(String key) {
        return listParameter.get(key);
    }

    public void checkMandatoryConfigurations() {
        for (String mandatoryConfig : mandatoryConfigs) {
            boolean exists = false;
            for (Map.Entry<String, Parameter> pair : listParameter.entrySet()) {
                if (pair.getKey().equalsIgnoreCase(mandatoryConfig) && !pair.getValue().getDescription().isEmpty()) {
                    exists = true;
                }
            }
            if (!exists) {
                String errorLog = String.format("A mandatory Configuration parameter is not found in DB: %s", mandatoryConfig);
                LOGGER.error(errorLog);
            }
        }

    }
}

