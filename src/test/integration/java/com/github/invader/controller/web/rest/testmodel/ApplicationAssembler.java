package com.github.invader.controller.web.rest.testmodel;

import com.github.invader.controller.web.rest.dto.ApplicationDto;

public class ApplicationAssembler {

    public String name = "app1";
    public String groupName = "g1";

    public ApplicationDto assembly() {
        ApplicationDto application = new ApplicationDto();
        application.setName(name);
        application.setGroupName(groupName);
        return application;
    }
}
