package com.github.invader.controller.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgentConfigurationResourceIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api");
    }

    @Test
    public void getHello() throws Exception {
        template.getForEntity("http://localhost:" + port + "/api/groups/g1/apps/a1/config", String.class);
        template.getForEntity("http://localhost:" + port + "/api/groups/g1/apps/a2/config", String.class);
        template.getForEntity("http://localhost:" + port + "/api/groups/g2/apps/a1/config", String.class);
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/api/groups", String.class);
        System.out.println(response.getBody());
//        assertThat(response.getBody(), equalTo("{\"name\":\"Sample dto\"}"));
    }
}
