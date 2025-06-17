package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.commands.CreatePlanSubscriptionCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CreatePlanSubscriptionResource;

public class CreatePlanSubscriptionCommandFromResourceAssembler {
    public static CreatePlanSubscriptionCommand toCommandFromResource(CreatePlanSubscriptionResource resource) {
        return new CreatePlanSubscriptionCommand(resource.userId(), resource.fitwisePlanId(), resource.subscriptionStartDate(), resource.endDate(), resource.notes());
    }
}
