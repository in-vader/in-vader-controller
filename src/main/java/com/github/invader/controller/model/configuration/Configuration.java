package com.github.invader.controller.model.configuration;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Configuration {

    @Id
    private Long id;

    private String applicationId;

    private String groupId;

    private ConfigurationData configurationData;

    public ConfigurationData getConfigurationData() {
        return configurationData;
    }

    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }
}
