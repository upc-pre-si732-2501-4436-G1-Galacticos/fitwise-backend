package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.commands.AddDietToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.AddWorkoutToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.CreateFitwisePlanCommand;


public interface FitwisePlanCommandService {
    Long handle(CreateFitwisePlanCommand command);
    void handle(AddWorkoutToFitwisePlanCommand command);
    void handle(AddDietToFitwisePlanCommand command);
}
