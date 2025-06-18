package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.domain.model.queries.GetActivePlanSubscriptionsByUserId;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionsByUserIdQuery;
import org.upc.fitwise.plan.domain.services.PlanSubscriptionQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanSubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlanSubscriptionQueryServiceImpl implements PlanSubscriptionQueryService {

    private final PlanSubscriptionRepository planSubscriptionRepository;

    public PlanSubscriptionQueryServiceImpl(PlanSubscriptionRepository planSubscriptionRepository) {
        this.planSubscriptionRepository = planSubscriptionRepository;
    }

    @Override
    public Optional<PlanSubscription> handle(GetPlanSubscriptionByIdQuery query) {
        return planSubscriptionRepository.findById(query.planSubscriptionId());
    }

    @Override
    public List<PlanSubscription> handle(GetPlanSubscriptionsByUserIdQuery query) {
        return planSubscriptionRepository.findByUserId(query.userId());
    }

    @Override
    public List<PlanSubscription> handle(GetActivePlanSubscriptionsByUserId query) {
        return planSubscriptionRepository.findByUserIdAndIsActive(query.userId(), true);
    }
}
