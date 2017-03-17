package com.github.invader.controller.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelayDto {

    private int min;
    private int max;

}