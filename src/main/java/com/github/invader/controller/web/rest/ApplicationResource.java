package com.github.invader.controller.web.rest;


import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.repository.application.ApplicationRepository;
import com.github.invader.controller.repository.configuration.ConfigurationRepository;
import com.github.invader.controller.repository.group.GroupRepository;
import com.github.invader.controller.services.UpdateService;
import com.github.invader.controller.web.rest.dto.ApplicationDto;
import com.github.invader.controller.web.rest.dto.ConfigDto;
import com.github.invader.controller.web.rest.dto.DtoMapper;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static javaslang.collection.List.ofAll;

@RestController
@RequestMapping("/api")
public class ApplicationResource {

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/apps/{appName}")
    public ApplicationDto getApplication(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName) {
        return Option.of(applicationRepository.findOne(new ApplicationId(appName, groupName)))
                .map(mapper::toApplicationDto)
                .getOrElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/apps")
    public Set<ApplicationDto> getApplications(@PathVariable("groupName") String groupName) {
        return ofAll(applicationRepository.findAll())
                .map(mapper::toApplicationDto).toJavaSet();

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupName}/apps/{appName}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName) {
        updateService.deleteApplication(groupName, appName);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupName}/apps")
    public ResponseEntity<Void> addApplication(@PathVariable("groupName") String groupName, @RequestBody ApplicationDto application) {
        updateService.addApplication(mapper.fromAppplicationDto(application, groupName));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/apps/{appName}/configs/{configId}")
    public ConfigDto getAppConfig(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName, @PathVariable("configId") String configId) {
        return configurationRepository.findByApplicationId(new ApplicationId(appName, groupName))
                .map(c -> mapper.toConfigDto(c))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/apps/{appName}/configs")
    public Set<ConfigDto> getAppConfigs(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName) {
        return configurationRepository.findByApplicationId(new ApplicationId(appName, groupName))
                .map(c -> mapper.toConfigDto(c))
                .map(c -> Collections.singleton(c))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }


    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupName}/apps/{appName}/configs")
    public void addAppConfig(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName, @RequestBody ConfigDto config) {
        ApplicationId applicationId = new ApplicationId(appName, groupName);
        validateApplication(groupName, applicationId);
        Option.of(config)
                .map(c -> mapper.fromConfigDto(c))
                .peek(c -> {
                    c.setApplicationId(applicationId);
                    c.setId(UUID.randomUUID().toString());
                    configurationRepository.save(c);
                });
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupName}/apps/{appName}/configs/{configId}")
    public void deleteAppConfig(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName, @PathVariable("configId") String configId) {
        ApplicationId applicationId = new ApplicationId(appName, groupName);
        validateApplication(groupName, applicationId);
        configurationRepository.findById(configId)
                .peek(config -> configurationRepository.delete(config));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/groups/{groupName}/apps/{appName}/configs/{configId}")
    public void updateAppConfig(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName, @PathVariable("configId") String configId, @RequestBody ConfigDto config) {
        ApplicationId applicationId = new ApplicationId(appName, groupName);
        validateApplication(groupName, applicationId);
        configurationRepository.findById(configId)
                .peek(c -> {
                    Configuration configuration = mapper.fromConfigDto(config);
                    configuration.setApplicationId(new ApplicationId(appName, groupName));
                    configurationRepository.save(configuration);
                });
    }

    private void validateApplication(String groupName, ApplicationId applicationId) {
        Option.of(groupRepository.findByName(groupName)).getOrElseThrow(() -> new EntityNotFoundException());
        Option.of(applicationRepository.findOne(applicationId)).getOrElseThrow(() -> new EntityNotFoundException());
    }


}