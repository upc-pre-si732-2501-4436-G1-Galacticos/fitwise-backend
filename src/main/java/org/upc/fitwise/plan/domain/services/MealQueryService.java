package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsByUserIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetMealByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MealQueryService {
    List<Meal> handle(GetAllMealsQuery query);
    List<Meal> handle(GetAllMealsByUserIdQuery query);
    Optional<Meal> handle(GetMealByIdQuery query);
}
