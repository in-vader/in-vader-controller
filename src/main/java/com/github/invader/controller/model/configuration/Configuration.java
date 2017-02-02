package com.github.invader.controller.model.configuration;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name="configuration_type")
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configurationIdGenerator")
    @SequenceGenerator(name = "configurationIdGenerator", sequenceName = "seq_configuration_id")
    private Long id;

    @Embedded
    private ConfigurationData configurationData = new ConfigurationData();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }

    public ConfigurationData getConfigurationData() {
        return configurationData;
    }

}
