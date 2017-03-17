package com.github.invader.controller.service;

import com.github.invader.controller.model.ConfigurationAssembler;
import com.github.invader.controller.model.ConfigurationAssert.ConfigurationDataAssert;
import com.github.invader.controller.repository.configuration.ConfigurationRepository;
import com.github.invader.controller.services.CurrentConfigurationService;
import com.github.invader.controller.services.Properties;
import javaslang.control.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CurrentConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @Mock
    private Properties properties;

    @InjectMocks
    private CurrentConfigurationService currentConfigurationService;

    private Option<ConfigurationAssembler> defaultConfigAssembler = Option.none();
    private Option<ConfigurationAssembler> appConfigAssembler = Option.none();
    private Option<ConfigurationAssembler> groupConfigAssembler = Option.none();

    private ConfigurationDataAssert currentConfig;

    @Test
    public void whenNoApplicationAngGroupDefinedThenDefaultConfig() {

        givenDefaultConfig()
                .withDelay(1, 11)
                .withFailure(0.1);

        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(1, 11)
                .hasFailure(0.1);
    }

    @Test
    public void whenNoApplicationDefinedThenGroupConfigOverDefault() {

        givenDefaultConfig();
        givenGroupConfig()
                .withDelay(2, 12)
                .withFailure(0.2);

        whenGetCurrentConfig();

        currentConfig
                .hasDelay(2, 12)
                .hasFailure(0.2);
    }

    @Test
    public void whenApplicationDefinedThenApplictionConfigAsCurrent() {

        givenDefaultConfig();
        givenGroupConfig();
        givenApplicationConfig()
                .withDelay(3, 13)
                .withFailure(0.3);


        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(3, 13)
                .hasFailure(0.3);
    }

    @Test
    public void whenNoFailureInGroupAndApplicationThenFailureTakenFromDefault() {

        givenDefaultConfig()
                .withFailure(0.1);
        givenGroupConfig()
                .withNoFailure();
        givenApplicationConfig()
                .withDelay(3, 13)
                .withNoFailure();

        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(3, 13)
                .hasFailure(0.1);
    }

    @Test
    public void whenNoDelayInAppAndThenTakenFromGroup() {

        givenDefaultConfig()
                .withDelay(1, 11);
        givenGroupConfig()
                .withDelay(2, 12);
        givenApplicationConfig()
                .withNoDelay();

        whenGetCurrentConfig();

        thenCurrentConfig()
                .hasDelay(2, 12);
    }

    private void whenGetCurrentConfig() {
        when(properties.getDefaultConfig()).thenReturn(defaultConfigAssembler.map(dc -> dc.configurationData()).get());
        when(configurationRepository.findCurrentByApplication(any())).thenReturn(appConfigAssembler.map(acd -> acd.configuration()));
        when(configurationRepository.findCurrentByGroup(any())).thenReturn(groupConfigAssembler.map(acd -> acd.configuration()));
        currentConfig = new ConfigurationDataAssert(currentConfigurationService.getCurrentConfiguration("groupId","applicationId"));
    }

    private ConfigurationDataAssert thenCurrentConfig() {
        return currentConfig;
    }

    private ConfigurationAssembler givenDefaultConfig() {
        ConfigurationAssembler configurationAssembler = new ConfigurationAssembler();
        defaultConfigAssembler = Option.of(configurationAssembler);
        return configurationAssembler;
    }

    private ConfigurationAssembler givenGroupConfig() {
        ConfigurationAssembler configurationAssembler = new ConfigurationAssembler();
        groupConfigAssembler = Option.of(configurationAssembler);
        return configurationAssembler;
    }

    private ConfigurationAssembler givenApplicationConfig() {
        ConfigurationAssembler configurationAssembler = new ConfigurationAssembler();
        appConfigAssembler = Option.of(configurationAssembler);
        return configurationAssembler;
    }

}
