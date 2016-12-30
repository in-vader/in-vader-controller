package com.github.invader.controller.web.rest;

import com.github.invader.controller.model.AgentConfiguration;
import com.github.invader.controller.repository.AgentConfigurationRepository;
import com.github.invader.controller.transport.Config;
import com.github.invader.controller.transport.Delay;
import com.github.invader.controller.transport.Failure;
import com.github.invader.controller.web.rest.dto.AgentConfigurationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AgentConfigurationResource {
    @Value("${invader.agent.delayMin}")
    private int delayMin;

    @Value("${invader.agent.delayMax}")
    private int delayMax;

    @Value("${invader.agent.failure}")
    private double failure;

    private static AtomicLong idGenerator = new AtomicLong(0);

    private final AgentConfigurationRepository repository;

    @Autowired
    public AgentConfigurationResource(AgentConfigurationRepository repository) {
        this.repository = repository;
    }

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

    @RequestMapping(method = RequestMethod.GET, path = "/groups/{group}/apps/{app}/config")
    public Config getConfig(@PathVariable("group") String group, @PathVariable("app") String app) {
        final Delay delay = new Delay(delayMin, delayMax);
        final Failure failure = new Failure(this.failure);

        return new Config(delay, failure);
    }
}