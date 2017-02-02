package com.github.invader.controller.web.rest;


import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.repository.ApplicationRepository;
import com.github.invader.controller.repository.GroupRepository;

import com.github.invader.controller.web.rest.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ApplicationResource {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DtoMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps/{appName}")
    public com.github.invader.controller.web.rest.dto.Application getApplication(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        return applicationRepository.findById(new ApplicationId(appName, groupId))
                .map(application -> mapper.toApplication(application))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps")
    public Set<com.github.invader.controller.web.rest.dto.Application> getApplications(@PathVariable("groupId") String groupId) {

        return groupRepository.findById(groupId)
                .map(group -> group.getApplications()).orElseThrow(() -> new EntityNotFoundException()).stream()
                .map(application -> new com.github.invader.controller.web.rest.dto.Application(
                        application.getId().getName(), application.getId().getGroupId(), application.getRegisteredAt(), application.getLastHeartbeat())).collect(Collectors.toSet());
    }


    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupId}/apps/{appName}")
    public void deletApplication(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        applicationRepository.findById(new ApplicationId(appName, groupId))
                .ifPresent(application -> applicationRepository.delete(application));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupId}/apps")
    public void addApplication(@PathVariable("groupId") String groupId, @RequestBody com.github.invader.controller.web.rest.dto.Application applicationDto) {
        groupRepository.findById(groupId).map(
                group -> {
                    Application application = mapper.fromAppplicationDto(applicationDto, groupId);
                    group.addApplication(application);
                    groupRepository.save(group);
                    return application;
                }
        ).orElseThrow(() -> new EntityNotFoundException());
    }


}