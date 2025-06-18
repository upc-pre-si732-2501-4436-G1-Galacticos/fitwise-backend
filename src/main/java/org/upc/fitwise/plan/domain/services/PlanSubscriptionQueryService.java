package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.domain.model.queries.GetActivePlanSubscriptionsByUserId;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlanSubscriptionQueryService {
    Optional<PlanSubscription> handle(GetPlanSubscriptionByIdQuery query);
    List<PlanSubscription> handle(GetPlanSubscriptionsByUserIdQuery query);
    List<PlanSubscription> handle(GetActivePlanSubscriptionsByUserId query);
}
