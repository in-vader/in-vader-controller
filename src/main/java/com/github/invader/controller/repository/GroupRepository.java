package com.github.invader.controller.repository;

import com.github.invader.controller.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Optional<Group> findById(String groupId);

}
