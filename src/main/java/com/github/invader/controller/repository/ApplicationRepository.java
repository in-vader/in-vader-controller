package com.github.invader.controller.repository;

import com.github.invader.controller.model.application.Application;
import com.github.invader.controller.model.application.ApplicationId;
import com.github.invader.controller.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {

    Set<Application> findByGroup(Group group);

    Optional<Application> findById(ApplicationId applicationId);

}
