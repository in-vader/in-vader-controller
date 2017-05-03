package com.github.invader.controller.web.rest.agentconfig;

import com.github.invader.controller.web.rest.testmodel.ApplicationAssembler;
import com.github.invader.controller.web.rest.testmodel.ConfigDtoAssembler;
import com.github.invader.controller.web.rest.testmodel.ConfigJsonAssert;
import com.github.invader.controller.web.rest.dto.GroupDto;
import com.github.invader.controller.web.rest.testmodel.RequestsSender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.time.LocalDateTime;

import static com.github.invader.controller.web.rest.testmodel.ConfigJsonAssert.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgentConfigurationResourceIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private MongoOperations mongoOperations;

    private RequestsSender requests;

    private ResponseEntity<String> response;

    private String groupName = "notExistingGroup";
    private String appName = "notExistingApp";

    private LocalDateTime now = LocalDateTime.now();

    @Before
    public void setUp() throws Exception {
        requests = new RequestsSender(new URL("http://localhost:" + port + "/api"), template);
    }

    @After
    public void cleanUp() {
        mongoOperations.dropCollection(com.github.invader.controller.model.configuration.Configuration.class);
        mongoOperations.dropCollection(com.github.invader.controller.model.application.Application.class);
        mongoOperations.dropCollection(com.github.invader.controller.model.group.Group.class);
    }

    @Test
    public void defaultAgentConfigForNotDefinedGroupAndApp() throws Exception {
        //given no groups or apps

        whenAgentConfigRequested();

        then(responseConfig()
            .hasFailure(0.5)
            .hasDelay(5, 5000));
    }

    @Test
    public void groupConfigOverDefaultOne() throws Exception {

        givenGroup();
        givenApplication();
        givenGroupConfig()
                .withDelay(0, 10)
                .withFailure(0.9);

        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(0, 10)
                .hasFailure(0.9));
    }

    @Test
    public void appConfigOverGroupAndDefaultConfigs() throws Exception {

        givenGroup();
        givenApplication();
        givenGroupConfig()
                .withDelay(0, 10)
                .withFailure(0.9);
        givenAppConfig()
                .withDelay(2, 20)
                .withFailure(0.2);

        whenAgentConfigRequested();

        then(responseConfig()
            .hasDelay(2, 20)
            .hasFailure(0.2));

    }

    @Test
    public void currentConfigPartiallyFromGroupConfigAndPartiallyFromAppConfig() throws Exception {

        givenGroup();
        givenApplication();
        givenGroupConfig()
                .withNoDelay()
                .withFailure(0.9);
        givenAppConfig()
                .withDelay(2, 20)
                .withNoFailure();

        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(2, 20)
                .hasFailure(0.9));

    }

    @Test
    public void currentConfigPartiallyFromApplicationConfigAndPartiallyFromDefault() throws Exception {

        givenGroup();
        givenApplication();
        givenAppConfig()
                .withDelay(2, 20)
                .withNoFailure();

        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(2, 20)
                .hasFailure(0.5));

    }

    @Test
    public void forMultiplyConfigsPerGroupCurrentIsTaken() throws Exception {

        givenGroup();
        givenGroupConfig()
                .withTrigger(now.minusDays(2));
        givenGroupConfig()
                .withDelay(1, 10)
                .withFailure(0.1)
                .withTrigger(now.minusDays(1));


        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(1, 10)
                .hasFailure(0.1));

    }

    @Test
    public void forMultiplyConfigsWithStartsNotDefinedCurrentIsTaken() throws Exception {

        givenGroup();
        givenGroupConfig()
                .withTrigger(null, now.plusDays(2));
        givenGroupConfig()
                .withDelay(1, 10)
                .withFailure(0.1)
                .withTrigger(null, now.plusDays(1));


        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(1, 10)
                .hasFailure(0.1));

    }

    @Test
    public void forMultiplyConfigsgroupsAndAppsCurrentFromAppIsTaken() throws Exception {

        givenGroup();
        givenApplication();
        givenGroupConfig()
                .withTrigger(now.minusDays(2), now.plusDays(2));
        givenGroupConfig()
                .withTrigger(now.minusDays(1), now.plusDays(1));

        givenAppConfig()
                .withTrigger(now.minusDays(2), now.plusDays(2));
        givenAppConfig()
                .withDelay(6, 60)
                .withFailure(0.6)
                .withTrigger(now.minusDays(1), now.plusDays(1));


        whenAgentConfigRequested();

        then(responseConfig()
                .hasDelay(6, 60)
                .hasFailure(0.6));

    }

    public ConfigJsonAssert responseConfig() {
        return new ConfigJsonAssert(response.getBody());
    }

    private void whenAgentConfigRequested() {
        requests.post();
        response = requests.getForAgentConfig(groupName, appName);
    }


    private void givenGroup() {
        GroupDto group = new GroupDto();
        groupName = "g1";
        group.setName(groupName);
        requests.setGroup(group);
    }

    private ApplicationAssembler givenApplication() {
        ApplicationAssembler applicationAssembler = new ApplicationAssembler();
        appName = applicationAssembler.name;
        requests.setApplication(applicationAssembler);
        return applicationAssembler;
    }

    private ConfigDtoAssembler givenAppConfig() {
        return givenConfig().forApp(groupName, appName);
    }

    private ConfigDtoAssembler givenGroupConfig() {
        return givenConfig().forGroup(groupName);
    }

    private ConfigDtoAssembler givenConfig() {
        ConfigDtoAssembler configAssembler = new ConfigDtoAssembler();
        requests.addConfig(configAssembler);
        return configAssembler;
    }
}
