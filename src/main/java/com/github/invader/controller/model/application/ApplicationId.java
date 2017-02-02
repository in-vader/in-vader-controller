package com.github.invader.controller.model.application;

import com.github.invader.controller.model.group.Group;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApplicationId implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private String groupId;

    public ApplicationId() {
    }

    public ApplicationId(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationId that = (ApplicationId) o;

        return Objects.equals(that.name, name) && Objects.equals(that.groupId, groupId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, groupId);
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }
}
