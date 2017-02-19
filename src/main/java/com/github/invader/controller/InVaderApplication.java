package com.github.invader.controller;

import com.github.invader.controller.service.InVaderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties(InVaderProperties.class)
@EnableMongoRepositories(basePackages = "com.github.invader.controller.repository")
public class InVaderApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InVaderApplication.class);
        app.run(args);
    }

}
