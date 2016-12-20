package com.github.invader.controller.model;

import javax.persistence.*;

@Entity
@Table(name = "configuration")
public class AgentConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "seq_agent_configuration")
    private Long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AgentConfiguration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
