package com.github.invader.controller.model;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import com.github.invader.controller.model.configuration.Trigger;
import javaslang.control.Option;

import java.time.LocalDateTime;

public class ConfigurationAssembler {

    private Option<Failure> failure = Option.of(new Failure(0.5));
    private Option<Delay> delay = Option.of(new Delay(0,1000));
    private Option<Trigger> trigger = Option.none();
    private Option<String> groupName = Option.none();
    private Option<ApplicationId> application = Option.none();

    public ConfigurationAssembler withFailure(Double failure) {
        this.failure = Option.of(new Failure(failure));
        return this;
    }

    public ConfigurationAssembler withNoFailure() {
        this.failure = Option.none();
        return this;
    }

    public ConfigurationAssembler withNoDelay() {
        this.delay = Option.none();
        return this;
    }

    public ConfigurationAssembler forGroup(String groupName) {
        this.groupName = Option.of(groupName);
        return this;
    }

    public ConfigurationAssembler forApplication(ApplicationId applicationId) {
        this.application = Option.of(applicationId);
        return this;
    }

    public ConfigurationAssembler withDelay(Integer min, Integer max) {
        this.delay = Option.of(new Delay(min, max));
        return this;
    }

    public ConfigurationAssembler withTrigger(LocalDateTime from, LocalDateTime to) {
        this.trigger = Option.of(new Trigger(from, to));
        return this;
    }


    public Configuration configuration() {
        Configuration configuration = new Configuration();
        ConfigurationData configurationData = new ConfigurationData();
        delay.peek(d -> configurationData.setDelay(d));
        failure.peek(f -> configurationData.setFailure(f));
        configuration.setConfigurationData(configurationData);
        trigger.peek(t -> configuration.setTrigger(t));
        groupName.peek(gn -> configuration.setGroupName(gn));
        application.peek(aid -> configuration.setApplicationId(aid));
        return configuration;
    }

    public ConfigurationData configurationData() {
        ConfigurationData configurationData = new ConfigurationData();
        delay.peek(d -> configurationData.setDelay(d));
        failure.peek(f -> configurationData.setFailure(f));
        return configurationData;
    }

}
