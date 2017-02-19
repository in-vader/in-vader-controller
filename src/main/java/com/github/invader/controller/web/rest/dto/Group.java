package com.github.invader.controller.web.rest.dto;

public class Group {

    private String id;
    private int size;

    public Group() {
    }

    public Group(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
