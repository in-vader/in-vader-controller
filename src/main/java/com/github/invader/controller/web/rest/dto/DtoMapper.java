package com.github.invader.controller.web.rest.dto;

import com.github.invader.controller.model.configuration.ConfigurationData;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public CurrentConfig toCurrentConfig(com.github.invader.controller.model.configuration.Configuration configuration) {
        ConfigurationData config = configuration.getConfigurationData();
        return new CurrentConfig(new Failure(config.getFailure().getProbability()),
                new Delay(config.getDelay().getMin(), config.getDelay().getMax()));
    }

    public com.github.invader.controller.model.Group fromGroupDto(Group groupDto) {
        return new com.github.invader.controller.model.Group(groupDto.getId());
    }

    public Group toGroupDto(com.github.invader.controller.model.Group group) {
        return new Group(group.getId());
    }


//    public Config toConfig(com.github.invader.controller.model.configuration.Configuration configuration) {
//        com.github.invader.controller.model.configuration.ConfigurationData configurationData = configuration.getConfigurationData();
//        Config config = new Config(new Failure(configurationData.getFailure().getProbabvility()),
//                new Delay(configurationData.getDelay().getMin(), configurationData.getDelay().getMax()));
//        config.setId(configuration.getId());
//        return config;
//    }
//
//    public com.github.invader.controller.model.configuration.ConfigurationData fromConfigDto(Config configDto) {
//        return new com.github.invader.controller.model.configuration.ConfigurationData(
//                new com.github.invader.controller.model.configuration.Delay(configDto.getDelay().getMin(), configDto.getDelay().getMax()),
//                new com.github.invader.controller.model.configuration.Failure(configDto.getFailure().getProbability())
//        );
//    }
//
//
//    public Application toApplication(com.github.invader.controller.model.Application application) {
//        return new Application(application.getId().getName(),
//                application.getId().getGroupId(),
//                application.getRegisteredAt(),
//                application.getLastHeartbeat());
//    }
//
//    public com.github.invader.controller.model.Application fromAppplicationDto(Application applicationDto, String groupId) {
//        return new com.github.invader.controller.model.Application(new ApplicationId(applicationDto.getName(), groupId));
//    }

}
