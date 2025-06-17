package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.CreateDietCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateDietResource;


public class CreateDietCommandFromResourceAssembler {
    public static CreateDietCommand toCommandFromResource(CreateDietResource resource, String username) {
        return new CreateDietCommand(resource.title(),resource.description(),username);
    }
}


