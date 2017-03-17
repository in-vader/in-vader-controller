package com.github.invader.controller.services;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.repository.configuration.ConfigurationRepository;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
public class CurrentConfigurationService {

    @Autowired
    private Properties properties;

    @Autowired
    private ConfigurationRepository configurationRepository;

    public ConfigurationData getCurrentConfiguration(String groupName, String applicationName) {
        return Stream.of(applicationConfigData(groupName, applicationName),
                    groupConfigData(groupName),
                    defaultConfig())
                .reduceLeft(this::merge)
                .get();
    }

    private Supplier<ConfigurationData> merge(Supplier<ConfigurationData> toSupplier, Supplier<ConfigurationData> fromSupplier) {
        return Option.of(toSupplier.get())
                    .map(to -> Option.of(to)
                        .filter(hasValuesNotSet())
                        .map(mergeConfigurationData(fromSupplier))
                        .getOrElse(() -> to))
                    .getOrElse(fromSupplier);

    }

    private Predicate<ConfigurationData> hasValuesNotSet() {
        return cd -> cd.getDelay() == null || cd.getFailure() == null;
    }

    private Function<ConfigurationData, Supplier<ConfigurationData>> mergeConfigurationData(Supplier<ConfigurationData> fromSupplier) {
        return to -> () -> to.merge(fromSupplier.get());
    }


    private Supplier<ConfigurationData> applicationConfigData(String groupName, String applicationName) {
        return () -> configurationRepository
                    .findCurrentByApplication(new ApplicationId(applicationName, groupName))
                    .map(c -> c.getConfigurationData())
                    .getOrElse(() -> null);
    }

    private Supplier<ConfigurationData> groupConfigData(String groupName) {
        return () -> configurationRepository
                    .findCurrentByGroup(groupName)
                    .map(c -> c.getConfigurationData())
                    .getOrElse(() -> null);
    }

    private Supplier<ConfigurationData> defaultConfig() {
        return () -> properties.getDefaultConfig();
    }

}
