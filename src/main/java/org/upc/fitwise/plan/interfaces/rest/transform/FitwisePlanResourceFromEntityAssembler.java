package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;

public class FitwisePlanResourceFromEntityAssembler {

    public static FitwisePlanResource toResourceFromEntity(FitwisePlan entity) {

        Long dietId = null;
        Long workoutId = null;

        if (entity.getDiet() != null) {
            dietId = entity.getDiet().getId();
        }
        if (entity.getWorkout() != null) {
            workoutId = entity.getWorkout().getId();
        }
        return new FitwisePlanResource(entity.getId(),entity.getTitle(),dietId,workoutId,entity.getDescription(),entity.getTagNamesAsArray());
    }
}
