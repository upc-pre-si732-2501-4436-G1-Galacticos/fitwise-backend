package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.commands.AddExerciseToWorkoutCommand;

public interface WorkoutCommandService {
    void handle(AddExerciseToWorkoutCommand command);
}
