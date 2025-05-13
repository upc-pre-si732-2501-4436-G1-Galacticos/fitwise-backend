package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;

public class FitwisePlanResourceFromEntityAssembler {
    public static FitwisePlanResource toResourceFromEntity(FitwisePlan entity) {
        return new FitwisePlanResource(entity.getId(),entity.getTitle(),entity.getDiet().getId(),entity.getWorkout().getId(),entity.getDescription(),entity.getTagNamesAsArray());
    }
}
