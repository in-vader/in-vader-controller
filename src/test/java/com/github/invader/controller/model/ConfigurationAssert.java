package com.github.invader.controller.model;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import com.github.invader.controller.model.configuration.Trigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ConfigurationAssert {

    final private Configuration configuration;
    final private ConfigurationDataAssert configurationDataAssert;
    final private TriggetAssert triggetAssert;


    public ConfigurationAssert(Configuration configuration) {
        this.configuration = configuration;
        this.configurationDataAssert = new ConfigurationDataAssert(configuration.getConfigurationData());
        this.triggetAssert = new TriggetAssert(configuration.getTrigger());
    }

    public ConfigurationDataAssert hasData() {
        return this.configurationDataAssert;
    }

    public TriggetAssert hasTrigger() {
        return this.triggetAssert;
    }

    public ConfigurationAssert isFromApplication(ApplicationId applicationId) {
        assertEquals(applicationId, configuration.getApplicationId());
        return this;
    }


    public static class TriggetAssert {

        private final Trigger trigger;


        public TriggetAssert(Trigger trigger) {
            this.trigger = trigger;
        }
    }

    public static class ConfigurationDataAssert {

        final private ConfigurationData configurationData;

        public ConfigurationDataAssert(ConfigurationData configurationData) {
            this.configurationData = configurationData;
        }

        public ConfigurationDataAssert hasFailure(Double failure) {
            assertNotNull(configurationData);
            assertEquals(new Failure(failure), configurationData.getFailure());
            return this;
        }

        public ConfigurationDataAssert hasFailureProbability(Double failureProbability) {
            assertNotNull(configurationData);
            assertNotNull(configurationData.getFailure());
            assertEquals(failureProbability, configurationData.getFailure().getProbability());
            return this;
        }

        public ConfigurationDataAssert hasDelay(Integer min, Integer max) {
            assertNotNull(configurationData);
            assertEquals(new Delay(min, max), configurationData.getDelay());
            return this;
        }

        public ConfigurationDataAssert hasDelayMin(Integer delayMin) {
            assertNotNull(configurationData);
            assertNotNull(configurationData.getDelay());
            assertEquals(delayMin, configurationData.getDelay().getMin());
            return this;
        }

        public ConfigurationDataAssert hasDelayMax(Integer delayMax) {
            assertNotNull(configurationData);
            assertNotNull(configurationData.getDelay());
            assertEquals(delayMax, configurationData.getDelay().getMax());
            return this;
        }

        public ConfigurationDataAssert sameAs(ConfigurationData configurationData) {
            assertEquals(configurationData, this.configurationData);
            return this;
        }

    }

}
