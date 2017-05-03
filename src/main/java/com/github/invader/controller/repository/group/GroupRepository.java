package com.github.invader.controller.repository.group;

import com.github.invader.controller.model.group.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Group findByName(String name);

}
