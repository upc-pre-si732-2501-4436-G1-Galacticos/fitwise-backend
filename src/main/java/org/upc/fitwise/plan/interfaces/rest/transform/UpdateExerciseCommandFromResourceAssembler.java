package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.UpdateExerciseCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateExerciseResource;

public class UpdateExerciseCommandFromResourceAssembler {
    public static UpdateExerciseCommand toCommandFromResource(Long exerciseId, UpdateExerciseResource resource,String username) {
        return new UpdateExerciseCommand(exerciseId, resource.title(), resource.description(),username);
    }
}