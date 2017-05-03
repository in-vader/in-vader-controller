package com.github.invader.controller.services;

import com.github.invader.controller.model.configuration.ConfigurationData;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "invader")
public class Properties {

    private ConfigurationData defaultConfig;

    public ConfigurationData getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(ConfigurationData defaultConfig) {
        this.defaultConfig = defaultConfig;
    }
}
