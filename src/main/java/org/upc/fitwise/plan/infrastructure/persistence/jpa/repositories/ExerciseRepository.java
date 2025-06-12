package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.Exercise;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByUserId(Long userId);
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
    boolean existsByUserIdAndTitle(Long userId,String title);
    Optional<Exercise> findByUserIdAndTitle(Long userId,String title);

}
