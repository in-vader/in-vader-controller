package com.github.invader.controller.model.configuration;

import javaslang.control.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationData {

    private Delay delay;

    private Failure failure;

    public ConfigurationData merge(ConfigurationData from) {
        return Option.of(from)
                .map(f -> new ConfigurationData(
                        this.getDelay() != null ? this.getDelay() : f.getDelay(),
                        this.getFailure() != null ? this.getFailure() : f.getFailure()))
                .getOrElse(this);
    }
}
