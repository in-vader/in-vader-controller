package com.github.invader.controller.model.configuration;

import com.github.invader.controller.model.application.ApplicationId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class Configuration {

    @Id
    private String id;

    @Field
    private ApplicationId applicationId;

    @Field
    private String groupName;

    @Field
    private ConfigurationData configurationData;

    @Field
    private Trigger trigger;

}
