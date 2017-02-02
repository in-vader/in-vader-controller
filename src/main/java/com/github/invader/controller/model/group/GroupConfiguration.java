package com.github.invader.controller.model.group;

import com.github.invader.controller.model.configuration.Configuration;

import javax.persistence.*;

@Entity
@DiscriminatorValue("GROUP")
public class GroupConfiguration extends Configuration {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

}
