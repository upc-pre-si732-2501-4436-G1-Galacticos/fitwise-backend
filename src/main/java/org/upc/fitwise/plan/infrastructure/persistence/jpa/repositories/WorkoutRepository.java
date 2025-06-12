package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserId(Long userId);
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
    boolean existsByUserIdAndTitle(Long userId,String title);
    Optional<Workout> findByUserIdAndTitle(Long userId,String title);
}