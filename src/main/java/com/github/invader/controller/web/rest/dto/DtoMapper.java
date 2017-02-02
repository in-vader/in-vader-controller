package com.github.invader.controller.web.rest.dto;

import com.github.invader.controller.model.application.ApplicationId;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public CurrentConfig toCurrentConfig(com.github.invader.controller.model.configuration.ConfigurationData configurationData) {
        return new CurrentConfig(new Failure(configurationData.getFailure().getProbabvility()),
                new Delay(configurationData.getDelay().getMin(), configurationData.getDelay().getMax()));
    }

    public Config toConfig(com.github.invader.controller.model.configuration.Configuration configuration) {
        com.github.invader.controller.model.configuration.ConfigurationData configurationData = configuration.getConfigurationData();
        Config config = new Config(new Failure(configurationData.getFailure().getProbabvility()),
                new Delay(configurationData.getDelay().getMin(), configurationData.getDelay().getMax()));
        config.setId(configuration.getId());
        return config;
    }

    public com.github.invader.controller.model.configuration.ConfigurationData fromConfigDto(Config configDto) {
        return new com.github.invader.controller.model.configuration.ConfigurationData(
                new com.github.invader.controller.model.configuration.Delay(configDto.getDelay().getMin(), configDto.getDelay().getMax()),
                new com.github.invader.controller.model.configuration.Failure(configDto.getFailure().getProbability())
        );
    }

    public com.github.invader.controller.model.group.Group fromGroupDto(Group groupDto) {
        com.github.invader.controller.model.group.Group group = new com.github.invader.controller.model.group.Group();
        group.setId(groupDto.getId());
        return group;
    }

    public Application toApplication(com.github.invader.controller.model.application.Application application) {
        return new Application(application.getId().getName(),
                application.getId().getGroupId(),
                application.getRegisteredAt(),
                application.getLastHeartbeat());
    }

    public com.github.invader.controller.model.application.Application fromAppplicationDto(Application applicationDto, String groupId) {
        return new com.github.invader.controller.model.application.Application(new ApplicationId(applicationDto.getName(), groupId));
    }

}
