package com.github.invader.controller.service;

import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class DefaultConfiguration implements Supplier<ConfigurationData> {

    @Value("${invader.agent.delayMin}")
    private int delayMin;

    @Value("${invader.agent.delayMax}")
    private int delayMax;

    @Value("${invader.agent.failure}")
    private double failure;

    @Override
    public ConfigurationData get() {
        return new ConfigurationData(new Delay(delayMin, delayMax), new Failure(failure));
    }
}
