package com.github.invader.controller.model.application;

import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.group.Group;
import com.github.invader.controller.model.group.GroupConfiguration;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "application")
public class Application {

    @EmbeddedId
    private ApplicationId id;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name="group_id")
    @Fetch(FetchMode.JOIN)
    private Group group;

    @OneToMany(mappedBy = "application")
    private Set<ApplicationConfiguration> configurations;

    @Column(name = "registered_ad")
    private LocalDateTime registeredAt;

    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    public Application() {
        registeredAt = LocalDateTime.now();
    }

    public Application(ApplicationId applicationId) {
        this();
        this.id = applicationId;
    }


    public ApplicationId getId() {
        return id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void updateLastHeartbeat() {
        this.lastHeartbeat = LocalDateTime.now();
    }

    public Set<ApplicationConfiguration> getConfigurations() {
        return configurations;
    }

    public Optional<ConfigurationData> getConfigurationData() {
        return configurations.stream().sorted((c1, c2) -> c1.getId().compareTo(c2.getId())).findFirst().map(c -> c.getConfigurationData());
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
}
