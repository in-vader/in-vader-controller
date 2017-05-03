package com.github.invader.controller.web.rest.testmodel;


import com.github.invader.controller.web.rest.dto.GroupDto;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestsSender {

    private final URL base;
    private final TestRestTemplate template;

    private List<GroupDto> groups = List.empty();
    private List<ApplicationAssembler> applications = List.empty();
    private List<ConfigDtoAssembler> configs = List.empty();

    public RequestsSender(URL base, TestRestTemplate template) {
        this.base = base;
        this.template = template;
    }

    public ResponseEntity<String> getForAgentConfig(String groupName, String appName) {
        return template.getForEntity(base + "/groups/" + groupName + "/apps/" + appName + "/agent-config", String.class);
    }

    public void post() {
        groups.forEach(g -> postGroup(g));
        applications.forEach(a -> postApplication(a));
        configs.forEach(c -> postConfig(c));
    }

    private void postConfig(ConfigDtoAssembler c) {
        if(c.appName.isDefined()) postAppConfig(c);
        else if (c.groupName.isDefined()) postGroupConfig(c);
    }

    private void postGroupConfig(ConfigDtoAssembler c) {
        postEntity("/groups/" + c.groupName.get() + "/configs", c.assembly());
    }

    private void postAppConfig(ConfigDtoAssembler c) {
        postEntity("/groups/" + c.groupName.get() + "/apps/" + c.appName.get() + "/configs", c.assembly());
    }

    private void postApplication(ApplicationAssembler a) {
        postEntity("/groups/" + a.groupName + "/apps", a.assembly());
    }

    private void postGroup(GroupDto g) {
        postEntity("/groups", g);
    }

    private void postEntity(String url, Object postedObject) {
        ResponseEntity<Void> responseEntity = template.postForEntity(base + url, postedObject, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public void setGroup(GroupDto group) {
        groups = groups.append(group);
    }

    public void setApplication(ApplicationAssembler application) {
        applications = applications.append(application);
    }

    public void addConfig(ConfigDtoAssembler configs) {
        this.configs = this.configs.append(configs);
    }
}
