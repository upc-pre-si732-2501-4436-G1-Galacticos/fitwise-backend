package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.domain.model.commands.CancelPlanSubscriptionCommand;
import org.upc.fitwise.plan.domain.model.commands.CreatePlanSubscriptionCommand;
import org.upc.fitwise.plan.domain.model.commands.RenewPlanSubscriptionCommand;

import java.util.Optional;

public interface PlanSubscriptionCommandService {
    Long handle(CreatePlanSubscriptionCommand command);
    Optional<PlanSubscription> handle(RenewPlanSubscriptionCommand command);
    void handle(CancelPlanSubscriptionCommand command);
}
