package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.commands.CreateMealCommand;
import org.upc.fitwise.plan.domain.model.commands.UpdateMealCommand;

import java.util.Optional;

public interface MealCommandService {
    Long handle(CreateMealCommand command);
    Optional<Meal> handle(UpdateMealCommand command);
}
