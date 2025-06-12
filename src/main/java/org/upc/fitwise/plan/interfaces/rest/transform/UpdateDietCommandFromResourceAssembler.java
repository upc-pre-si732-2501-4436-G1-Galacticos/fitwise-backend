package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.UpdateDietCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateDietResource;

public class UpdateDietCommandFromResourceAssembler {
    public static UpdateDietCommand toCommandFromResource(Long dietId, UpdateDietResource resource, String username) {
        return new UpdateDietCommand(dietId, resource.title(), resource.description(),username);
    }
}