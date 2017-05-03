package com.github.invader.controller.web.rest.dto;

import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.model.configuration.Delay;
import com.github.invader.controller.model.configuration.Failure;
import com.github.invader.controller.model.configuration.Trigger;
import com.github.invader.controller.model.group.Group;
import javaslang.control.Option;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public CurrentConfigDto toCurrentConfig(ConfigurationData config) {
        return new CurrentConfigDto(new FailureDto(config.getFailure().getProbability()),
                new DelayDto(config.getDelay().getMin(), config.getDelay().getMax()));
    }

    public Group fromGroupDto(GroupDto groupDto) {
        return new Group(groupDto.getName());
    }

    public GroupDto toGroupDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setName(group.getName());
        return groupDto;
    }


    public ConfigDto toConfigDto(Configuration configuration) {
        ConfigurationData configurationData = configuration.getConfigurationData();
        ConfigDto config = new ConfigDto();
        config.setId(configuration.getId());
        Option.of(configurationData).map(cd -> cd.getFailure()).peek(f -> config.setFailure(new FailureDto(f.getProbability())));
        Option.of(configurationData).map(cd -> cd.getDelay()).peek(d -> config.setDelay(new DelayDto(d.getMin(), d.getMax())));
        Option.of(configuration.getTrigger()).peek(c -> config.setTrigger(new TriggerDto(c.getStarts(), c.getEnds())));
        return config;
    }

    public Configuration fromConfigDto(ConfigDto configDto) {
        Configuration configuration = new Configuration();
        ConfigurationData configurationData = new ConfigurationData();
        Option.of(configDto.getDelay()).peek(d -> configurationData.setDelay(new Delay(d.getMin(), d.getMax())));
        Option.of(configDto.getFailure()).peek(f -> configurationData.setFailure(new Failure(f.getProbability())));
        Option.of(configDto.getTrigger()).peek(t -> configuration.setTrigger(new Trigger(t.getStarts(), t.getEnds())));
        configuration.setConfigurationData(configurationData);
        return configuration;

    }


    public ApplicationDto toApplicationDto(Application application) {
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setName(application.getId().getName());
        applicationDto.setGroupName(application.getId().getGroupName());
        applicationDto.setRegisteredAt(applicationDto.getRegisteredAt());
        applicationDto.setLastHeartbeat(applicationDto.getLastHeartbeat());
        return applicationDto;
    }

    public Application fromAppplicationDto(ApplicationDto applicationDto, String groupName) {
        return new Application(new ApplicationId(applicationDto.getName(), groupName));
    }

}
