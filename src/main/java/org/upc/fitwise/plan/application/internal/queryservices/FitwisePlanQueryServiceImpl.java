package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalProfileService;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.queries.*;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FitwisePlanQueryServiceImpl implements FitwisePlanQueryService {

    private final FitwisePlanRepository fitwisePlanRepository;
    private final PlanTagRepository planTagRepository;
    private final ExternalProfileService externalProfileService;

    public FitwisePlanQueryServiceImpl(FitwisePlanRepository fitwisePlanRepository, PlanTagRepository planTagRepository, ExternalProfileService externalProfileService) {
        this.fitwisePlanRepository = fitwisePlanRepository;
        this.planTagRepository = planTagRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public List<FitwisePlan> handle(GetAllFitwisePlansQuery query) {
        return fitwisePlanRepository.findAll();
    }
    @Override
    public Optional<FitwisePlan> handle(GetFitwisePlanByIdQuery query) {
        return fitwisePlanRepository.findById(query.fitwisePlanId());
    }

    @Override
    public Optional<FitwisePlan> handle(GetFitwisePlanByWorkoutIdQuery query) {
        return fitwisePlanRepository.findByWorkoutId(query.workoutId());
    }

    @Override
    public Optional<FitwisePlan> handle(GetFitwisePlanByDietIdQuery query) {
        return fitwisePlanRepository.findByDietId(query.dietId());
    }

    @Override
    public List<FitwisePlan> handle(GetFitwisePlansRecomendedByProfileIdQuery query) {
        List<PlanTag> recommendedPlanTags = new ArrayList<>();
        List<String> activityLevelTags = externalProfileService.fetchProfileActivityLevelTagsByProfileId(query.profileId());
        List<String> goalTags = externalProfileService.fetchProfileGoalTagsByProfileId(query.profileId());
        recommendedPlanTags.addAll(planTagRepository.findPlanTagsByTitleIn(activityLevelTags));
        recommendedPlanTags.addAll(planTagRepository.findPlanTagsByTitleIn(goalTags));
        return fitwisePlanRepository.findFitwisePlansByTagsIn(recommendedPlanTags.stream().distinct().toList());
    }

}
