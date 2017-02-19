package com.github.invader.controller.model;

import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import javaslang.control.Option;

public class ConfigurationDataAssembler {

    private Option<Failure> failure = Option.of(new Failure(0.5));
    private Option<Delay> delay = Option.of(new Delay(0,1000));

    public ConfigurationDataAssembler withFailure(Failure failure) {
        this.failure = Option.of(failure);
        return this;
    }

    public ConfigurationDataAssembler withNoFailure() {
        this.failure = Option.none();
        return this;
    }

    public ConfigurationDataAssembler withNoDelay() {
        this.delay = Option.none();
        return this;
    }


    public ConfigurationDataAssembler withDelay(Delay delay) {
        this.delay = Option.of(delay);
        return this;
    }

    public ConfigurationData assembly() {
        ConfigurationData configurationData = new ConfigurationData();
        delay.peek(d -> configurationData.setDelay(d));
        failure.peek(f -> configurationData.setFailure(f));
        return configurationData;
    }


}
