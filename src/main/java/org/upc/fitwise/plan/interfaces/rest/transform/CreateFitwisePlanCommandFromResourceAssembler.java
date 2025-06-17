package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.commands.CreateFitwisePlanCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateFitwisePlanResource;

public class CreateFitwisePlanCommandFromResourceAssembler {
    public static CreateFitwisePlanCommand toCommandFromResource(CreateFitwisePlanResource resource) {
        return new CreateFitwisePlanCommand(resource.title(), resource.description(),resource.tagNames() );
    }
}
