package com.github.invader.controller.web.rest.dto;

import lombok.Data;

@Data
public class ConfigDto {

    private String id;
    private FailureDto failure;
    private DelayDto delay;
    private TriggerDto trigger;

}