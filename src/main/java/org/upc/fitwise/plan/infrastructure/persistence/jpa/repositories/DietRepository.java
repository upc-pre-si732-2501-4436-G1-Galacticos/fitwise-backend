package org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;

import java.util.List;
import java.util.Optional;


@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    List<Diet> findAllByUserId(Long userId);
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
    Optional<Diet> findByUserIdAndTitle(Long userId, String title);
    boolean existsByUserIdAndTitle(Long userId,String title);

}