package com.github.invader.controller.model.configuration;

import java.util.Objects;

public class ConfigurationData {

    private Delay delay;

    private Failure failure;

    public ConfigurationData() {

    }

    public ConfigurationData(Delay delay, Failure failure) {
        this.delay = delay;
        this.failure = failure;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
    }

    public Delay getDelay() {
        return delay;
    }

    public Failure getFailure() {
        return failure;
    }

    public ConfigurationData merge(ConfigurationData configurationData) {
        return new ConfigurationData(
                delay == null ? configurationData.delay : delay,
                failure == null ? configurationData.failure : failure
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationData other = (ConfigurationData) o;
        return Objects.equals(other.delay, delay) && Objects.equals(other.failure, failure);

    }

    @Override
    public int hashCode() {
        return Objects.hash(delay, failure);
    }


    @Override
    public String toString() {
        return "ConfigurationData{" +
                "delay=" + delay +
                ", failure=" + failure +
                '}';
    }
}
