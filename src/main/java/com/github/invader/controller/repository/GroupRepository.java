package com.github.invader.controller.repository;

import com.github.invader.controller.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    Optional<Group> findById(String groupId);

}
