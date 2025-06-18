package org.upc.fitwise.plan.domain.model.commands;

import java.time.LocalDate;

public record RenewPlanSubscriptionCommand(
        Long planSubscriptionId,
        LocalDate newEndDate) {
}
