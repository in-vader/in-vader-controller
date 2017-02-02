package com.github.invader.controller.web.rest.dto;

import java.time.LocalDateTime;

public class Application {

    private String name;
    private String groupId;
    private LocalDateTime registeredAt;
    private LocalDateTime lastHeartbeat;

    public Application() {

    }

    public Application(String name, String groupId, LocalDateTime registeredAt, LocalDateTime lastHeartbeat) {
        this.name = name;
        this.groupId = groupId;
        this.registeredAt = registeredAt;
        this.lastHeartbeat = lastHeartbeat;
    }

    public String getName() {
        return name;
    }

}
