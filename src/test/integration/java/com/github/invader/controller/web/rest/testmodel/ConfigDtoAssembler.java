package com.github.invader.controller.web.rest.testmodel;

import com.github.invader.controller.web.rest.dto.ConfigDto;
import com.github.invader.controller.web.rest.dto.DelayDto;
import com.github.invader.controller.web.rest.dto.FailureDto;
import com.github.invader.controller.web.rest.dto.TriggerDto;
import javaslang.control.Option;

import java.time.LocalDateTime;

public class ConfigDtoAssembler {

    public Option<FailureDto> failure = Option.of(new FailureDto(0.0));
    public Option<DelayDto> delay = Option.of(new DelayDto(0,1000));
    public Option<String> appName = Option.none();
    public Option<String> groupName = Option.none();
    public Option<TriggerDto> trigger = Option.none();

    public ConfigDtoAssembler forApp(String groupName, String appName) {
        this.groupName = Option.of(groupName);
        this.appName = Option.of(appName);
        return this;
    }

    public ConfigDtoAssembler forGroup(String groupName) {
        this.groupName = Option.of(groupName);
        return this;
    }

    public ConfigDtoAssembler withFailure(Double failure) {
        this.failure = Option.of(new FailureDto(failure));
        return this;
    }

    public ConfigDtoAssembler withNoFailure() {
        this.failure = Option.none();
        return this;
    }

    public ConfigDtoAssembler withNoDelay() {
        this.delay = Option.none();
        return this;
    }

    public ConfigDtoAssembler withTrigger(LocalDateTime starts, LocalDateTime ends) {
        this.trigger = Option.of(new TriggerDto(starts, ends));
        return this;
    }

    public ConfigDtoAssembler withTrigger(LocalDateTime starts) {
        return withTrigger(starts, null);
    }


    public ConfigDtoAssembler withDelay(Integer min, Integer max) {
        this.delay = Option.of(new DelayDto(min, max));
        return this;
    }

    public ConfigDto assembly() {
        ConfigDto config = new ConfigDto();
        delay.peek(d -> config.setDelay(d));
        failure.peek(f -> config.setFailure(f));
        trigger.peek(t -> config.setTrigger(t));
        return config;
    }


}
