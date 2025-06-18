package org.upc.fitwise.plan.interfaces.rest.transform;

import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.interfaces.rest.resources.PlanSubscriptionResource;

public class PlanSubscriptionResourceFromEntityAssembler {

    public static PlanSubscriptionResource toResourceFromEntity(PlanSubscription subscription) {
        return new PlanSubscriptionResource(
                subscription.getId(),
                subscription.getFitwisePlan() != null ? subscription.getFitwisePlan().getId() : null,
                subscription.getUserId(),
                subscription.getSubscriptionStartDate(),
                subscription.getEndDate(),
                subscription.isActive(),
                subscription.getNotes()
        );
    }
}
