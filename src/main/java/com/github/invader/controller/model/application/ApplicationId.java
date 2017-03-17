package com.github.invader.controller.model.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationId implements Serializable {

    private String name;

    private String groupName;

}
