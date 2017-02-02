package com.github.invader.controller.web.rest.dto;

public class Group {

    private String id;
    private int size;

    public Group() {
    }

    public Group(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

}
