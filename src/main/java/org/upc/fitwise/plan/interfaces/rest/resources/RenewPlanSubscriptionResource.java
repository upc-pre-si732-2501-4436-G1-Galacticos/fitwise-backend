package org.upc.fitwise.plan.interfaces.rest.resources;

import java.time.LocalDate;

public record RenewPlanSubscriptionResource(Long planSubscriptionId, LocalDate newEndDate) {
}
