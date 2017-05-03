package com.github.invader.controller.repository.configuration;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import javaslang.collection.List;
import javaslang.control.Option;

public interface ConfigurationProvider {

    Option<Configuration> findCurrentByGroup(String groupName);

    Option<Configuration> findCurrentByApplication(ApplicationId applicationId);

}
