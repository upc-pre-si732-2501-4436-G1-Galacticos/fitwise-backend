package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.queries.GetAllPlanTagsQuery;

import java.util.List;

public interface PlanTagQueryService {
    List<PlanTag> handle(GetAllPlanTagsQuery query);

}
