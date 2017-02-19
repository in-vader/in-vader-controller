package com.github.invader.controller.service;

import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.repository.ConfigurationRepository;
import javaslang.control.Either;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConfigurationService {

    @Autowired
    private InVaderProperties properties;

    @Autowired
    private ConfigurationRepository configurationRepository;

    public ConfigurationData getCurrentConfiguration(String groupId, String applicationId) {
        ConfigurationData defaultConfiguration = properties.getDefaultConfig();
        return Option.of(
                configurationRepository.findByApplicationId(applicationId))
                .map(c -> c.getConfigurationData())
                .map(ac -> ac.merge(
                        Option.of(
                                configurationRepository.findByGroupId(groupId))
                                .map(c -> c.getConfigurationData())
                                .map(gc -> gc.merge(defaultConfiguration))
                        .getOrElse(defaultConfiguration)
                    ))
                .getOrElse(defaultConfiguration);
    }

}
