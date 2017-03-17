package com.github.invader.controller.web.rest;


import com.github.invader.controller.services.CurrentConfigurationService;
import com.github.invader.controller.services.UpdateService;
import com.github.invader.controller.web.rest.dto.CurrentConfigDto;
import com.github.invader.controller.web.rest.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConfigurationResource {

    @Autowired
    private CurrentConfigurationService currentConfigurationService;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private UpdateService updateService;

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupName}/apps/{appName}/agent-config")
    public CurrentConfigDto getCurrent(@PathVariable("groupName") String groupName, @PathVariable("appName") String appName) {
        updateService.currentConfigRequested(groupName, appName);
        return mapper.toCurrentConfig(currentConfigurationService.getCurrentConfiguration(groupName, appName));
    }

}
