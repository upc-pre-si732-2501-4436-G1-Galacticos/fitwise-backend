package org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;

import java.util.List;

@Repository
public interface ActivityLevelRepository extends JpaRepository<ActivityLevel, Long> {
    boolean existsByLevelName(String title);
}