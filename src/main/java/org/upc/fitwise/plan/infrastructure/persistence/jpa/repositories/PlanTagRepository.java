package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;


import java.util.List;
import java.util.Optional;

@Repository
public interface PlanTagRepository extends JpaRepository<PlanTag, Long> {
    Optional<PlanTag> findByTitle(String title);
    List<PlanTag> findPlanTagsByTitleIn(List<String> title);
}
