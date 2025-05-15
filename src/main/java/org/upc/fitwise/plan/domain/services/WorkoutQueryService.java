package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetWorkoutByIdQuery;

import java.util.List;
import java.util.Optional;


public interface WorkoutQueryService {
    List<Workout> handle(GetAllWorkoutsQuery query);
    Optional<Workout> handle(GetWorkoutByIdQuery query);

}
