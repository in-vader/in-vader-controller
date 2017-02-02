package com.github.invader.controller.web.rest.dto;

public class Config {

    private Long id;
    private String groupId;
    private Failure failure;
    private Delay delay;

    public Config(){

    }

    public Config(Failure failure, Delay delay) {
        this.failure = failure;
        this.delay = delay;
    }


    public Failure getFailure() {
        return failure;
    }

    public Delay getDelay() {
        return delay;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}