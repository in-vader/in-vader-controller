package com.github.invader.controller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupCollection")
public class Group {

    @Id
    private String id;

    public Group(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                '}';
    }
}
