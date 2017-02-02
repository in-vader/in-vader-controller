package com.github.invader.controller.web.rest;

import com.github.invader.controller.repository.*;
import com.github.invader.controller.web.rest.dto.DtoMapper;
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
    private DtoMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public Set<com.github.invader.controller.web.rest.dto.Group> getGroups() {
        return groupRepository.findAll().stream().map(g -> new com.github.invader.controller.web.rest.dto.Group(g.getId(), g.getApplications().size()))
                .collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{id}")
    public com.github.invader.controller.web.rest.dto.Group getGroup(@PathVariable("id") String groupId) {
        return groupRepository.findById(groupId)
                .map(group -> new com.github.invader.controller.web.rest.dto.Group(group.getId(), group.getApplications().size()))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") String groupId) {
        groupRepository.findById(groupId).map(group -> {
            groupRepository.delete(group);
            return group;
        });
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups")
    public void deleteGroup(@RequestBody com.github.invader.controller.web.rest.dto.Group group) {
        groupRepository.save(mapper.fromGroupDto(group));
    }


}