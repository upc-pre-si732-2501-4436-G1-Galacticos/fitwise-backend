package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.UpdateMealCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateMealResource;

public class UpdateMealCommandFromResourceAssembler {
    public static UpdateMealCommand toCommandFromResource(Long mealId, UpdateMealResource resource,String username) {
        return new UpdateMealCommand(mealId, resource.title(), resource.description(),username);
    }
}