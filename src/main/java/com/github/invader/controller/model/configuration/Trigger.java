package com.github.invader.controller.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trigger {

    private LocalDateTime starts;

    private LocalDateTime ends;

}
