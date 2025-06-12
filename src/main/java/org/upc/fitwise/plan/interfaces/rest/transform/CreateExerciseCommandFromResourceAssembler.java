package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.commands.CreateExerciseCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateExerciseResource;


public class CreateExerciseCommandFromResourceAssembler {
    public static CreateExerciseCommand toCommandFromResource(CreateExerciseResource resource,String username) {
        return new CreateExerciseCommand(resource.title(),resource.description(),username);
    }
}


