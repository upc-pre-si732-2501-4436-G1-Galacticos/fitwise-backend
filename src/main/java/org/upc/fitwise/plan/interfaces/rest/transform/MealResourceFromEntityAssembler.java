package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.interfaces.rest.resources.MealResource;

public class MealResourceFromEntityAssembler {
    public static MealResource toResourceFromEntity(Meal entity) {
        return new MealResource(entity.getId(),entity.getTitle(),entity.getDescription());
    }
}
