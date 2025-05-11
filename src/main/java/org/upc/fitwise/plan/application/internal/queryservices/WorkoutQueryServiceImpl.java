package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.queries.GetAllFitwisePlansQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByWorkoutIdQuery;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.domain.services.WorkoutQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutQueryServiceImpl implements WorkoutQueryService {

    private final WorkoutRepository workoutRepository;

    public WorkoutQueryServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> handle(GetAllWorkoutsQuery query) {
        return workoutRepository.findAll();
    }


}
