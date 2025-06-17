package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.commands.CreateMealCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateMealResource;


public class CreateMealCommandFromResourceAssembler {
    public static CreateMealCommand toCommandFromResource(CreateMealResource resource,String username) {
        return new CreateMealCommand(resource.title(),resource.description(),username);
    }
}


