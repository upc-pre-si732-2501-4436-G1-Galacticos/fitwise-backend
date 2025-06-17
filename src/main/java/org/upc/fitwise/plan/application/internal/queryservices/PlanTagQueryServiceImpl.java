package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.queries.GetAllPlanTagsQuery;
import org.upc.fitwise.plan.domain.services.PlanTagQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;

import java.util.List;

@Service
public class PlanTagQueryServiceImpl implements PlanTagQueryService {

    private final PlanTagRepository planTagRepository;

    public PlanTagQueryServiceImpl(PlanTagRepository planTagRepository) {
        this.planTagRepository = planTagRepository;
    }

    @Override
    public List<PlanTag> handle(GetAllPlanTagsQuery query) {
        return planTagRepository.findAll();
    }


}
