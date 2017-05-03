package com.github.invader.controller.model.group;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "groupCollection")
public class Group {

    @Id
    private String name;

}
