package com.github.invader.controller.model.group;


import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name="application_group")
public class Group {

    @Id
    private String id;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupConfiguration> configurations = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Application> applications = new HashSet<>();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
        application.setGroup(this);
    }

    public void addConfiguration(ConfigurationData configurationData) {
        GroupConfiguration groupConfiguration = new GroupConfiguration();
        groupConfiguration.setConfigurationData(configurationData);
        this.configurations.add(groupConfiguration);
        groupConfiguration.setGroup(this);
    }

    public void removeConfiguration(Configuration configuration) {
        this.configurations.remove(configuration);
    }


    public Optional<Application> getApplicaton(ApplicationId applicationId) {
        return applications.stream().filter(a -> a.getId().equals(applicationId)).findFirst();
    }

    public Set<GroupConfiguration> getConfigurations() {
        return configurations;
    }

    public Optional<ConfigurationData> getConfigurationData() {
        return configurations.stream().sorted((c1, c2) -> c1.getId().compareTo(c2.getId())).findFirst().map(c -> c.getConfigurationData());
    }


    public Group() {
    }

    public Group(String id) {
        this.id = id;
    }
}
