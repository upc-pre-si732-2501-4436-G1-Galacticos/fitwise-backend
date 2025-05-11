package org.upc.fitwise.plan.domain.services;




import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsQuery;

import java.util.List;


public interface WorkoutQueryService {
    List<Workout> handle(GetAllWorkoutsQuery query);

}
