package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.queries.*;


import java.util.List;
import java.util.Optional;

public interface FitwisePlanQueryService {
    List<FitwisePlan> handle(GetAllFitwisePlansQuery query);
    Optional<FitwisePlan> handle(GetFitwisePlanByIdQuery query);
    Optional<FitwisePlan> handle(GetFitwisePlanByWorkoutIdQuery query);
    Optional<FitwisePlan> handle(GetFitwisePlanByDietIdQuery query);
    List<FitwisePlan> handle(GetFitwisePlansRecomendedByProfileIdQuery query);

}
