package com.github.invader.controller.web.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationDto {

    private String name;
    private String groupName;
    private LocalDateTime registeredAt;
    private LocalDateTime lastHeartbeat;


}
