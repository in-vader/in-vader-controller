package com.github.invader.controller.repository.application;

import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import javaslang.control.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, ApplicationId> {

    Integer countByGroupName(String groupName);

    Long deleteByGroupName(String groupName);

}
