package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.CreateWorkoutCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateWorkoutResource;


public class CreateWorkoutCommandFromResourceAssembler {
    public static CreateWorkoutCommand toCommandFromResource(CreateWorkoutResource resource, String username) {
        return new CreateWorkoutCommand(resource.title(),resource.description(),username);
    }
}


