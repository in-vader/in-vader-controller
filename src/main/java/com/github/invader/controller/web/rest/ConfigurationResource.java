package com.github.invader.controller.web.rest;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.repository.ConfigurationRepository;
import com.github.invader.controller.repository.GroupRepository;
import com.github.invader.controller.service.ConfigurationService;

import com.github.invader.controller.web.rest.dto.Config;
import com.github.invader.controller.web.rest.dto.CurrentConfig;
import com.github.invader.controller.web.rest.dto.DtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConfigurationResource {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private ConfigurationRepository<Configuration> configurationRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps/{appName}/agent-config")
    public CurrentConfig getCurrent(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        return mapper.toCurrentConfig(configurationService.getCurrentConfiguration(groupId, new ApplicationId(appName, groupId)));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/configs/{configId}")
    public Config getGroupConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId) {
        return groupRepository.findById(groupId)
                .map(group -> group.getConfigurations()).orElseThrow(() -> new EntityNotFoundException()).stream()
                .filter(c -> c.getId().equals(configId)).findFirst()
                .map(c -> {
                        Config config = mapper.toConfig(c);
                        config.setGroupId(groupId);
                        return config;})
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/configs")
    public Set<Config> getGroupConfigs(@PathVariable("groupId") String groupId) {
        return groupRepository.findById(groupId)
                .map(group -> group.getConfigurations())
                .orElseThrow(() -> new EntityNotFoundException()).stream()
                .map(c -> {
                        Config config = mapper.toConfig(c);
                        config.setGroupId(groupId);
                        return config;})
                .collect(Collectors.toSet());
    }


    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupId}/configs")
    public void addConfig(@PathVariable("groupId") String groupId, @RequestBody Config config) {
        groupRepository.findById(groupId)
                .map(group -> {
                        group.addConfiguration(mapper.fromConfigDto(config));
                        groupRepository.save(group);
                        return group;})
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupId}/configs/{configId}")
    public void deleteConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId) {
        groupRepository.findById(groupId).ifPresent(group ->
                configurationRepository.findById(configId).ifPresent(configuration -> {
                        group.removeConfiguration(configuration);
                        configurationRepository.delete(configuration);}));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/groups/{groupId}/configs/{configId}")
    public void updateConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId, @RequestBody Config config) {
        groupRepository.findById(groupId).map(group -> {
            configurationRepository.findById(configId).map(configuration -> {
                ConfigurationData configurationData = mapper.fromConfigDto(config);
                configuration.setConfigurationData(configurationData);
                configurationRepository.save(configuration);
                return configuration;
            }).orElseThrow(() -> new EntityNotFoundException());
            return group;
        }).orElseThrow(() -> new EntityNotFoundException());
    }



}
