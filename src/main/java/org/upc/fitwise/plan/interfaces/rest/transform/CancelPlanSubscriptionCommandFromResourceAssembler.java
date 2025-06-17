package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.commands.CancelPlanSubscriptionCommand;
import org.upc.fitwise.plan.interfaces.rest.resources.CancelPlanSubscriptionResource;

public class CancelPlanSubscriptionCommandFromResourceAssembler {
    public static CancelPlanSubscriptionCommand toCommandFromResource(CancelPlanSubscriptionResource resource) {
        return new CancelPlanSubscriptionCommand(resource.planSubscriptionId());
    }
}
