package com.github.invader.controller.service;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.ConfigurationData;
import com.github.invader.controller.repository.ApplicationRepository;

import com.github.invader.controller.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConfigurationService {

    @Autowired
    private DefaultConfiguration defaultConfiguration;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public ConfigurationData getCurrentConfiguration(String groupId, ApplicationId applicationId) {

        ConfigurationData defaultConfigData = defaultConfiguration.get();

        ConfigurationData groupMergedData = groupRepository.findById(groupId)
                .map(group -> group.getConfigurationData()
                        .map(groupConfigData -> groupConfigData.merge(defaultConfigData))
                        .orElse(defaultConfigData))
                .orElse(defaultConfigData);

        return applicationRepository.findById(applicationId)
                .map(application -> {
                    application.updateLastHeartbeat();
                    applicationRepository.save(application);
                    return application.getConfigurationData()
                                    .map(appConfigData -> appConfigData.merge(groupMergedData))
                                    .orElse(groupMergedData);})
                .orElse(groupMergedData);

    }

}
