package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;

public class WorkoutResourceFromEntityAssembler {
    public static WorkoutResource toResourceFromEntity(Workout entity) {
        return new WorkoutResource(entity.getId(),entity.getTitle(), entity.getNote(),entity.getExercises());
    }
}
