package com.github.invader.controller.web.rest.testmodel;

import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class ConfigJsonAssert {

    private final String config;

    private String failure;
    private String delayMin;
    private String delayMax;

    public ConfigJsonAssert(String config) {
        this.config = config;
    }

    public ConfigJsonAssert hasFailure(Double failure) {
        this.failure = String.valueOf(failure);
        return this;
    }

    public ConfigJsonAssert hasDelay(Integer min, Integer max) {
        this.delayMin = String.valueOf(min);
        this.delayMax = String.valueOf(max);
        return this;
    }

    private void check() {
        assertThat(config,
                sameJSONAs("{\"delay\":{\"min\":" + delayMin + ",\"max\":" + delayMax + "},\"failure\":{\"probability\":" + failure + "}}"));

    }

    public static void then(ConfigJsonAssert configJsonAssert) {
        configJsonAssert.check();
    }

}
