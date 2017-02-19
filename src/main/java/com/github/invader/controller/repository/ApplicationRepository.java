package com.github.invader.controller.repository;

import com.github.invader.controller.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {

    Integer countByGroupId(String groupId);

}
