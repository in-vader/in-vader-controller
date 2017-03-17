package com.github.invader.controller.web.rest;

import com.github.invader.controller.repository.application.ApplicationRepository;
import com.github.invader.controller.repository.configuration.ConfigurationRepository;
import com.github.invader.controller.repository.group.GroupRepository;
import com.github.invader.controller.services.UpdateService;
import com.github.invader.controller.web.rest.dto.ConfigDto;
import com.github.invader.controller.web.rest.dto.DtoMapper;
import com.github.invader.controller.web.rest.dto.GroupDto;
import javaslang.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static javaslang.collection.List.ofAll;


@RestController
@RequestMapping("/api")
public class GroupsResource {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private DtoMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public Set<GroupDto> getGroups() {
        return ofAll(groupRepository.findAll())
                .map(mapper::toGroupDto).toJavaSet();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{name}")
    public GroupDto getGroup(@PathVariable("name") String groupName) {
        return Option.of(groupRepository.findByName(groupName))
                .map(mapper::toGroupDto)
                .peek(group -> group.setSize(applicationRepository.countByGroupName(groupName)))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") String groupName) {
        updateService.deleteGroup(groupName);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups")
    public ResponseEntity<Void> addGroup(@RequestBody GroupDto group) {
        updateService.addGroup(mapper.fromGroupDto(group));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/configs/{configId}")
    public ConfigDto getGroupConfig(@PathVariable("groupName") String groupName, @PathVariable("configId") String configId) {
        return configurationRepository.findById(configId)
                .map(c -> mapper.toConfigDto(c))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/configs")
    public Set<ConfigDto> getGroupConfigs(@PathVariable("groupName") String groupName) {
        return configurationRepository.findByGroupName(groupName)
                .map(c -> mapper.toConfigDto(c))
                .map(c -> Collections.singleton(c))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }


    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupName}/configs")
    public void addGroupConfig(@PathVariable("groupName") String groupName, @RequestBody ConfigDto config) {
        validateGroup(groupName);
        Option.of(config)
                .map(c -> mapper.fromConfigDto(c))
                .peek(c -> {
                    c.setGroupName(groupName);
                    c.setId(UUID.randomUUID().toString());
                    configurationRepository.save(c);
                });
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupName}/configs/{configId}")
    public void deleteGroupConfig(@PathVariable("groupName") String groupName, @PathVariable("configId") String configId) {
        validateGroup(groupName);
        configurationRepository.findById(configId)
                .peek(config -> configurationRepository.delete(config));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/groups/{groupName}/configs/{configId}")
    public void updateGroupConfig(@PathVariable("groupName") String groupName, @PathVariable("configId") String configId, @RequestBody ConfigDto config) {
        validateGroup(groupName);
        configurationRepository.findById(configId)
                .peek(c -> configurationRepository.save(c));
    }

    private void validateGroup(String groupName) {
        Option.of(groupRepository.findByName(groupName)).getOrElseThrow(() -> new EntityNotFoundException());
    }



}