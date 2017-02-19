package com.github.invader.controller.repository;


import com.github.invader.controller.model.configuration.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends MongoRepository<Configuration, String> {

    Configuration findByApplicationId(String applicationId);

    Configuration findByGroupId(String groupId);

}
