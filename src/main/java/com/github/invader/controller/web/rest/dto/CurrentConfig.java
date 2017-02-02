package com.github.invader.controller.web.rest.dto;

public class CurrentConfig {

    private final Failure failure;
    private final Delay delay;

    public CurrentConfig(Failure failure, Delay delay) {
        this.failure = failure;
        this.delay = delay;
    }

    public Failure getFailure() {
        return failure;
    }

    public Delay getDelay() {
        return delay;
    }
}
