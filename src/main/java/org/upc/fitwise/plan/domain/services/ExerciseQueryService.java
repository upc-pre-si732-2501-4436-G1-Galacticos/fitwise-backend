package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesByUserIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesQuery;
import org.upc.fitwise.plan.domain.model.queries.GetExerciseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExerciseQueryService {
    List<Exercise> handle(GetAllExercisesQuery query);
    List<Exercise> handle(GetAllExercisesByUserIdQuery query);
    Optional<Exercise> handle(GetExerciseByIdQuery query);
}
