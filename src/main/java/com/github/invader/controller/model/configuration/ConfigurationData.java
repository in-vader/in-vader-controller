package com.github.invader.controller.model.configuration;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class ConfigurationData {

    @Embedded
    private Delay delay;

    @Embedded
    private Failure failure;

    public ConfigurationData() {

    }

    public ConfigurationData(Delay delay, Failure failure) {
        this.delay = delay;
        this.failure = failure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationData configuration = (ConfigurationData) o;
        return Objects.equals(configuration.delay, delay) && Objects.equals(configuration.failure, failure);

    }

    @Override
    public int hashCode() {
        return Objects.hash(delay, failure);
    }

    public Delay getDelay() {
        return delay;
    }

    public Failure getFailure() {
        return failure;
    }

    public ConfigurationData merge(ConfigurationData merged) {
        if(merged == null) {
            return this;
        }
        return new ConfigurationData(
              delay == null ? merged.getDelay() : delay, failure == null ? merged.failure : failure
        );
    }
}
