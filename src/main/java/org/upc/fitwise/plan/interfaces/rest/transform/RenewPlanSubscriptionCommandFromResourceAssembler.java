package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.commands.RenewPlanSubscriptionCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.RenewPlanSubscriptionResource;

public class RenewPlanSubscriptionCommandFromResourceAssembler {
    public static RenewPlanSubscriptionCommand toCommandFromResource(Long planSubscriptionId, RenewPlanSubscriptionResource resource) {
        return new RenewPlanSubscriptionCommand(planSubscriptionId, resource.newEndDate());
    }
}
