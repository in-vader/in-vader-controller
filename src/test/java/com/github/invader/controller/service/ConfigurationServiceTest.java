package com.github.invader.controller.service;

import com.github.invader.controller.model.ConfigurationDataAssembler;
import com.github.invader.controller.model.ConfigurationDataAssert;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import com.github.invader.controller.repository.ConfigurationRepository;
import javaslang.control.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @Mock
    private InVaderProperties properties;

    @InjectMocks
    private ConfigurationService configurationService;

    private Option<ConfigurationDataAssembler> defaultConfigAssembler = Option.none();
    private Option<ConfigurationDataAssembler> appConfigAssembler = Option.none();
    private Option<ConfigurationDataAssembler> groupConfigAssembler = Option.none();

    private ConfigurationData defaultConfig;
    private ConfigurationData appConfig;
    private ConfigurationData groupConfig;


    private ConfigurationDataAssert currentConfig;

    @Test
    public void whenNoApplicationAngGroupDefinedThenDefaultConfig() {

        givenDefaultConfig();

        whenGetCurrentConfig();

        thenCurrentConfig()
                .sameAs(defaultConfig);
    }

    @Test
    public void whenNoApplicationDefinedThenGroupConfigOverDefault() {

        givenDefaultConfig();
        givenGroupConfig()
                .withDelay(new Delay(1,10))
                .withFailure(new Failure(0.1));

        whenGetCurrentConfig();

        currentConfig
                .sameAs(groupConfig);
    }

    @Test
    public void whenApplicationDefinedThenApplictionConfigAsCurrent() {

        givenDefaultConfig();
        givenGroupConfig();
        givenApplicationConfig()
                .withDelay(new Delay(10,100))
                .withFailure(new Failure(0.9));


        whenGetCurrentConfig();

        thenCurrentConfig()
                .sameAs(groupConfig);
    }

    @Test
    public void whenNoFailureInGroupAndApplicationThenFailureTakenFromDefault() {

        givenDefaultConfig();
        givenGroupConfig().withNoFailure();
        givenApplicationConfig().withNoFailure();

        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(appConfig.getDelay())
                .hasFailure(defaultConfig.getFailure());
    }

    @Test
    public void whenNoDelayInAppAndThenTakenFromGroup() {

        givenDefaultConfig();
        givenGroupConfig().withDelay(new Delay(2,20));
        givenApplicationConfig().withNoDelay();

        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(groupConfig.getDelay())
                .hasFailure(appConfig.getFailure());
    }



    private void whenGetCurrentConfig() {
        defaultConfigAssembler.map(dca -> defaultConfig = dca.assembly())
                .peek(dc -> when(properties.getDefaultConfig()).thenReturn(dc));
        appConfigAssembler.map(aca -> appConfig = aca.assembly())
                .peek(ac -> when(configurationRepository.findByApplicationId(anyString())).thenReturn(asConfiguration(ac)));
        groupConfigAssembler.map(gca -> groupConfig = gca.assembly())
                .peek(gc -> when(configurationRepository.findByApplicationId(anyString())).thenReturn(asConfiguration(gc)));

        currentConfig = new ConfigurationDataAssert(configurationService.getCurrentConfiguration("groupId","applicationId"));
    }

    private ConfigurationDataAssert thenCurrentConfig() {
        return currentConfig;
    }

    private ConfigurationDataAssembler givenDefaultConfig() {
        defaultConfigAssembler = Option.of(new ConfigurationDataAssembler());
        return defaultConfigAssembler.get();
    }

    private ConfigurationDataAssembler givenGroupConfig() {
        groupConfigAssembler = Option.of(new ConfigurationDataAssembler());
        return groupConfigAssembler.get();
    }

    private ConfigurationDataAssembler givenApplicationConfig() {
        appConfigAssembler = Option.of(new ConfigurationDataAssembler());
        return appConfigAssembler.get();
    }

    private Configuration asConfiguration(ConfigurationData configurationData) {
        Configuration configuration = new Configuration();
        configuration.setConfigurationData(configurationData);
        return configuration;
    }
}
