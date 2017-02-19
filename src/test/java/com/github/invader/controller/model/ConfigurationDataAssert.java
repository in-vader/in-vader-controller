package com.github.invader.controller.model;

import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import static org.junit.Assert.*;

public class ConfigurationDataAssert {

    final private ConfigurationData configurationData;

    public ConfigurationDataAssert(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }

    public ConfigurationDataAssert hasFailure(Failure failure) {
        assertNotNull(configurationData);
        assertEquals(failure, configurationData.getFailure());
        return this;
    }

    public ConfigurationDataAssert hasFailureProbability(Double failureProbability) {
        assertNotNull(configurationData);
        assertNotNull(configurationData.getFailure());
        assertEquals(failureProbability, configurationData.getFailure().getProbability());
        return this;
    }

    public ConfigurationDataAssert hasDelay(Delay delay) {
        assertNotNull(configurationData);
        assertEquals(delay, configurationData.getDelay());
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
