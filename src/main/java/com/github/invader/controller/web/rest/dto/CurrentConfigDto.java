package com.github.invader.controller.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentConfigDto {

    private FailureDto failure;
    private DelayDto delay;

}
