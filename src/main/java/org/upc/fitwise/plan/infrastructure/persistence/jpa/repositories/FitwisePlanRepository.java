package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;

import java.util.List;
import java.util.Optional;

@Repository
public interface FitwisePlanRepository extends JpaRepository<FitwisePlan, Long> {
    boolean existsByTitle(String title);
    Optional<FitwisePlan> findByTitle(String title);
    Optional<FitwisePlan> findById(Long fitwisePlanId);
    Optional<FitwisePlan> findByWorkoutId(Long workoutId);
    Optional<FitwisePlan> findByDietId(Long dietId);
    List<FitwisePlan> findFitwisePlansByTagsIn(List<PlanTag> tags);
    boolean existsByUserIdAndTitle(Long userId, String title);





}