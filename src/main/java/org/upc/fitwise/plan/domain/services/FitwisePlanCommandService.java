package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.commands.*;


public interface FitwisePlanCommandService {
    Long handle(CreateFitwisePlanCommand command);
    void handle(AddWorkoutToFitwisePlanCommand command);
    void handle(AddDietToFitwisePlanCommand command);
    void handle(RemoveWorkoutToFitwisePlanCommand command);
    void handle(RemoveDietToFitwisePlanCommand command);
}
