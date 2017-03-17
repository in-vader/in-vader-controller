package com.github.invader.controller.model.application;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "application")
public class Application {

    @Id
    @NonNull
    private ApplicationId id;

    @Field
    private String groupName;

    @Field
    private LocalDateTime registeredAt;

    @Field
    private LocalDateTime lastHeartbeat;

}
