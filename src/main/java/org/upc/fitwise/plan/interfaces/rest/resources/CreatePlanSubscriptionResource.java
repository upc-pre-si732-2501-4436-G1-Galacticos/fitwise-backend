package org.upc.fitwise.plan.interfaces.rest.resources;

import java.time.LocalDate;

public record CreatePlanSubscriptionResource(
        Long userId,
        Long fitwisePlanId,
        LocalDate subscriptionStartDate,
        LocalDate endDate,
        String notes
) {
}
