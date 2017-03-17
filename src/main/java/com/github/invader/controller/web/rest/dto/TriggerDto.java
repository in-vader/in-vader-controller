package com.github.invader.controller.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TriggerDto {

    private LocalDateTime starts;
    private LocalDateTime ends;

}
