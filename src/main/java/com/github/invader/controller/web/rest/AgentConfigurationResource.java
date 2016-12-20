package com.github.invader.controller.web.rest;

import com.github.invader.controller.model.AgentConfiguration;
import com.github.invader.controller.repository.AgentConfigurationRepository;
import com.github.invader.controller.web.rest.dto.AgentConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/configuration")
public class AgentConfigurationResource {

    private static AtomicLong idGenerator = new AtomicLong(0);

    @Autowired
    private AgentConfigurationRepository repository;

    @RequestMapping("/get")
    public AgentConfigurationDto get() {
        return new AgentConfigurationDto("Sample dto");
    }

    @RequestMapping("/getAll")
    public List<AgentConfigurationDto> getAll() {
        return repository.findAll().stream().map( c -> new AgentConfigurationDto(c.getName()) )
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void add(@RequestBody AgentConfigurationDto agentConfgurationDto) {
        AgentConfiguration agentConfiguration = new AgentConfiguration();
        agentConfiguration.setName(agentConfgurationDto.getName());
        agentConfiguration.setId(idGenerator.addAndGet(1));
        repository.save(agentConfiguration);
    }
}
