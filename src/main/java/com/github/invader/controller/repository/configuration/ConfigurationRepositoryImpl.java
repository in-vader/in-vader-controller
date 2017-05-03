package com.github.invader.controller.repository.configuration;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import com.github.invader.controller.services.TriggerComparator;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ConfigurationRepositoryImpl implements ConfigurationProvider {

    @Autowired
    private MongoTemplate mongo;

    @Override
    public Option<Configuration> findCurrentByGroup(String groupName) {
        Query query = new Query();
        query.addCriteria(where("groupName").is(groupName));
        query.addCriteria(currentConfig());
        return List.ofAll(mongo.find(query, Configuration.class)).sorted(new TriggerComparator()).peekOption();

    }

    @Override
    public Option<Configuration> findCurrentByApplication(ApplicationId applicationId) {
        Query query = new Query();
        query.addCriteria(where("applicationId").is(applicationId));
        query.addCriteria(currentConfig());
        return List.ofAll(mongo.find(query, Configuration.class)).sorted(new TriggerComparator()).peekOption();
    }

    private Criteria currentConfig() {
        LocalDateTime now = LocalDateTime.now();
        return new Criteria().orOperator(where("trigger").exists(false),
                where("trigger.starts").is(null),
                where("trigger.starts").lte(now),
                where("trigger.ends").is(null),
                where("trigger.ends").gt(now));
    }

}
