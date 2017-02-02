package com.github.invader.controller.repository;

import com.github.invader.controller.model.configuration.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository<T extends Configuration> extends JpaRepository<T, Long> {

    Optional<Configuration> findById(Long id);

}
