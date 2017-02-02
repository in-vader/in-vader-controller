package com.github.invader.controller.model.application;

import com.github.invader.controller.model.configuration.Configuration;

import javax.persistence.*;

@Entity
@DiscriminatorValue("APP")
public class ApplicationConfiguration extends Configuration {

    @ManyToOne
    @JoinColumns({@JoinColumn(name="application_id"), @JoinColumn(name="group_id")})
    private Application application;

}
