package com.github.invader.controller.service;

import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import com.github.invader.controller.web.rest.dto.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@ConfigurationProperties(prefix = "invader")
public class InVaderProperties {

    private ConfigurationData defaultConfig;

    public ConfigurationData getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(ConfigurationData defaultConfig) {
        this.defaultConfig = defaultConfig;
    }
}
