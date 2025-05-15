package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.commands.AddExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.AddMealToDietCommand;

public interface DietCommandService {
    void handle(AddMealToDietCommand command);
}
