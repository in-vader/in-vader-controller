package com.github.invader.controller.web.rest;


import com.github.invader.controller.repository.GroupRepository;

import com.github.invader.controller.web.rest.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api")
public class ApplicationResource {

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps/{appName}")
    public com.github.invader.controller.web.rest.dto.Application getApplication(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps")
    public Set<com.github.invader.controller.web.rest.dto.Application> getApplications(@PathVariable("groupId") String groupId) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupId}/apps/{appName}")
    public void deletApplication(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupId}/apps")
    public void addApplication(@PathVariable("groupId") String groupId, @RequestBody com.github.invader.controller.web.rest.dto.Application applicationDto) {
        throw new RuntimeException("Not implemented yet");
    }


}