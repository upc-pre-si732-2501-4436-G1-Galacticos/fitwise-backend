package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.UpdateWorkoutCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateWorkoutResource;

public class UpdateWorkoutCommandFromResourceAssembler {
    public static UpdateWorkoutCommand toCommandFromResource(Long workoutId, UpdateWorkoutResource resource, String username) {
        return new UpdateWorkoutCommand(workoutId, resource.title(), resource.description(),username);
    }
}