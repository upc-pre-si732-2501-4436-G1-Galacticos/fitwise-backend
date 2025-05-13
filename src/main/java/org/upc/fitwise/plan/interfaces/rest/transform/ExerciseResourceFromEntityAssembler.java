package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.interfaces.rest.resources.ExerciseResource;

public class ExerciseResourceFromEntityAssembler {
    public static ExerciseResource toResourceFromEntity(Exercise entity) {
        return new ExerciseResource(entity.getId(),entity.getTitle(),entity.getDescription());
    }
}
