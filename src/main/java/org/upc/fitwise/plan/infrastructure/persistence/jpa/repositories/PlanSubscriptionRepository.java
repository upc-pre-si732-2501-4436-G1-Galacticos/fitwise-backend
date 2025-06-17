package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanSubscriptionRepository extends JpaRepository<PlanSubscription, Long> {
    List<PlanSubscription> findByUserId(Long userId);
    List<PlanSubscription> findByUserIdAndIsActive(Long userId, boolean isActive);
}
