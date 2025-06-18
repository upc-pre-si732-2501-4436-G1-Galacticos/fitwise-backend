package org.upc.fitwise.plan.domain.model.commands;

import java.time.LocalDate;

public record CreatePlanSubscriptionCommand(
        Long userId,
        Long fitwisePlanId,
        LocalDate subscriptionStartDate,
        LocalDate endDate,
        String notes) {
}
