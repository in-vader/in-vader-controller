package com.github.invader.controller.services;

import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.group.Group;
import com.github.invader.controller.repository.application.ApplicationRepository;
import com.github.invader.controller.repository.configuration.ConfigurationRepository;
import com.github.invader.controller.repository.group.GroupRepository;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void currentConfigRequested(String groupName, String appName) {
        Option.of(applicationRepository.findOne(new ApplicationId(appName, groupName)))
                .peek(app -> {
                    app.setLastHeartbeat(LocalDateTime.now());
                    applicationRepository.save(app);
                });
    }

    public void addGroup(Group group) {
        if(!Option.of(groupRepository.findByName(group.getName())).isDefined()) {
            groupRepository.save(group);
        }
    }

    public void deleteGroup(String groupName) {
        Option.of(groupRepository.findByName(groupName))
                .peek(group -> {
                    configurationRepository.deleteByGroupName(groupName);
                    applicationRepository.deleteByGroupName(groupName);
                    groupRepository.delete(groupName);
                });
    }

    public void addApplication(Application application) {
        Option.of(groupRepository.findByName(application.getId().getGroupName()))
                .peek(group -> {
                    if(Option.of(applicationRepository.findOne(application.getId())).isEmpty()) {
                        application.setRegisteredAt(LocalDateTime.now());
                        applicationRepository.save(application);
                    }
                });
    }

    public void deleteApplication(String groupName, String appName) {
        Option.of(groupRepository.findByName(groupName))
                .peek(group -> {
                    ApplicationId applicationId = new ApplicationId(appName, groupName);
                    applicationRepository.delete(applicationId);
                    configurationRepository.deleteByApplicationId(applicationId);
                });
    }
}
