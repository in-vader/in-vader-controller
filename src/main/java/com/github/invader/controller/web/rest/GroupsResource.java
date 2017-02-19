package com.github.invader.controller.web.rest;

import com.github.invader.controller.repository.ApplicationRepository;
import com.github.invader.controller.repository.GroupRepository;
import com.github.invader.controller.web.rest.dto.DtoMapper;
import javaslang.control.Option;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class GroupsResource {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DtoMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public Set<com.github.invader.controller.web.rest.dto.Group> getGroups() {
        return groupRepository.findAll().stream()
                .map(mapper::toGroupDto)
                .collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{id}")
    public com.github.invader.controller.web.rest.dto.Group getGroup(@PathVariable("id") String groupId) {
        return Try.of(() -> groupRepository.findOne(groupId))
                .map(mapper::toGroupDto)
                .peek(group -> group.setSize(applicationRepository.countByGroupId(groupId)))
                .getOrElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") String groupId) {
        Option.of(groupRepository.findOne(groupId))
                .peek(group -> groupRepository.delete(group));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups")
    public ResponseEntity<?> addGroup(@RequestBody com.github.invader.controller.web.rest.dto.Group group) {
        groupRepository.save(mapper.fromGroupDto(group));
        return ResponseEntity.ok().build();
    }

}