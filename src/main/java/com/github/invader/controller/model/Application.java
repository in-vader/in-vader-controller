package com.github.invader.controller.model;

import com.github.invader.controller.model.configuration.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "application")
public class Application {

    @Id
    private String id;

    private String groupId;

    private Set<Configuration> configurations;

    private LocalDateTime registeredAt;
    private LocalDateTime lastHeartbeat;

    public Application() {
    }

    public Application(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", configurations=" + configurations +
                ", registeredAt=" + registeredAt +
                ", lastHeartbeat=" + lastHeartbeat +
                '}';
    }
}
