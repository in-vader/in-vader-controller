package com.github.invader.controller.service;


import com.github.invader.controller.model.ConfigurationAssembler;
import com.github.invader.controller.model.ConfigurationAssert;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.services.TriggerComparator;
import javaslang.collection.List;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TriggerComparatorTest {

    private TriggerComparator underTest = new TriggerComparator();

    private List<ConfigurationAssembler> configurations = List.empty();

    private ConfigurationAssert result;

    private ApplicationId app1 = new ApplicationId("app1", "g1");
    private ApplicationId app2 = new ApplicationId("app2", "g1");
    private ApplicationId app3 = new ApplicationId("app3", "g1");
    private LocalDateTime now = LocalDateTime.now();

    @Test
    public void currentConfigIsTheOneWithLatestStartDate() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(minusDays(1), plusDays(3));
        givenAppConfig(app2)
                .withTrigger(minusDays(2), plusDays(3));
        givenAppConfig(app3)
                .withTrigger(minusDays(3), plusDays(3));

        whenFetchCurrent();

        thenResult()
            .isFromApplication(app1);

    }

    @Test
    public void currentConfigIsTheOneWithLatestStartDateBeforeNullDate() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(null, plusDays(3));
        givenAppConfig(app2)
                .withTrigger(minusDays(1), plusDays(3));
        givenAppConfig(app3)
                .withTrigger(minusDays(2), plusDays(3));

        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }

    @Test
    public void forSameStartDateNearestEndDateWins() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(minusDays(1), plusDays(3));
        givenAppConfig(app2)
                .withTrigger(minusDays(1), plusDays(2));
        givenAppConfig(app3)
                .withTrigger(minusDays(1), plusDays(3));

        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }

    @Test
    public void forSameNullStartDateNearestEndDateWins() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(null, plusDays(3));
        givenAppConfig(app2)
                .withTrigger(null, plusDays(2));
        givenAppConfig(app3)
                .withTrigger(null, plusDays(3));

        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }


    @Test
    public void forSameStartDateNearestEndDateWinsOverNullLastDate() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(minusDays(1), null);
        givenAppConfig(app2)
                .withTrigger(minusDays(1), plusDays(2));
        givenAppConfig(app3)
                .withTrigger(minusDays(1), null);

        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }

    @Test
    public void forAllStartNullsNearestEndDateWinsOverNullLastDate() throws InterruptedException {

        givenAppConfig(app1)
                .withTrigger(null, null);
        givenAppConfig(app2)
                .withTrigger(null, plusDays(2));
        givenAppConfig(app3)
                .withTrigger(null, plusDays(3));

        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }

    @Test
    public void definedTriggerBeforeNotDefined() throws InterruptedException {

        givenAppConfig(app1);
        givenAppConfig(app2)
                .withTrigger(minusDays(1), plusDays(1));
        givenAppConfig(app3)
                .withTrigger(minusDays(2), plusDays(2));


        whenFetchCurrent();

        thenResult()
                .isFromApplication(app2);

    }

    @Test
    public void definedStartsBeforeNotDefinedTriggerAndNotDefinedStarts() throws InterruptedException {

        givenAppConfig(app1);
        givenAppConfig(app2)
                .withTrigger(null, plusDays(2));
        givenAppConfig(app3)
                .withTrigger(minusDays(1), null);


        whenFetchCurrent();

        thenResult()
                .isFromApplication(app3);

    }

    private void whenFetchCurrent() {
        result = new ConfigurationAssert(configurations.map(ca -> ca.configuration()).sorted(underTest).peek());
    }

    private ConfigurationAssembler givenAppConfig(ApplicationId application) {
        ConfigurationAssembler configurationAssembler = new ConfigurationAssembler();
        configurations = configurations.append(configurationAssembler);
        configurationAssembler.forApplication(application);
        return configurationAssembler;
    }

    private LocalDateTime minusDays(Integer days) {
        return now.minusDays(days);
    }

    private LocalDateTime plusDays(Integer days) {
        return now.plusDays(days);
    }

    private ConfigurationAssert thenResult() {
        return result;
    }

}
