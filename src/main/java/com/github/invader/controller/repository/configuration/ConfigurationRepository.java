package com.github.invader.controller.repository.configuration;

import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.configuration.Configuration;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends MongoRepository<Configuration, String>, ConfigurationProvider {

    List<Configuration> findByGroupName(String groupName);

    List<Configuration> findByApplicationId(ApplicationId applicationId);

    Option<Configuration> findById(String configId);

    Integer deleteByGroupName(String groupName);

    Integer deleteByApplicationId(ApplicationId applicationId);

}
