package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;

public class FitwisePlanResourceFromEntityAssembler {
    public static FitwisePlanResource toResourceFromEntity(FitwisePlan entity) {
        return new FitwisePlanResource(entity.getId(),entity.getTitle(),entity.getDietId(),entity.getWorkoutId(),entity.getNote(),entity.getTagNamesAsArray());
    }
}
