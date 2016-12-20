package com.github.invader.controller.repository;

import com.github.invader.controller.model.AgentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentConfigurationRepository extends JpaRepository<AgentConfiguration, Long> {
}
