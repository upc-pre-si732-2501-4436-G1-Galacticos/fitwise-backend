package org.upc.fitwise.plan.interfaces.rest.resources;

import java.time.LocalDate;

public record PlanSubscriptionResource(
        Long id,
        Long fitwisePlanId,
        Long userId,
        LocalDate subscriptionStartDate,
        LocalDate endDate,
        boolean isActive,
        String notes) {
}
