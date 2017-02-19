package com.github.invader.controller.web.rest;


import com.github.invader.controller.web.rest.dto.Config;
import com.github.invader.controller.web.rest.dto.CurrentConfig;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ConfigurationResource {


    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/apps/{appName}/agent-config")
    public CurrentConfig getCurrent(@PathVariable("groupId") String groupId, @PathVariable("appName") String appName) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/configs/{configId}")
    public Config getGroupConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}/configs")
    public Set<Config> getGroupConfigs(@PathVariable("groupId") String groupId) {
        throw new RuntimeException("Not implemented yet");
    }


    @RequestMapping(method = RequestMethod.POST, path = "/groups/{groupId}/configs")
    public void addConfig(@PathVariable("groupId") String groupId, @RequestBody Config config) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupId}/configs/{configId}")
    public void deleteConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId) {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/groups/{groupId}/configs/{configId}")
    public void updateConfig(@PathVariable("groupId") String groupId, @PathVariable("configId") Long configId, @RequestBody Config config) {
        throw new RuntimeException("Not implemented yet");
    }



}
