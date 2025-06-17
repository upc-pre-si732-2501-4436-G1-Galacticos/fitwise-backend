package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.commands.AddExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.CreateWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.RemoveExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.UpdateWorkoutCommand;

import java.util.Optional;

public interface WorkoutCommandService {
    void handle(AddExerciseToWorkoutCommand command);
    void handle(RemoveExerciseToWorkoutCommand command);
    Long handle(CreateWorkoutCommand command);
    Optional<Workout> handle(UpdateWorkoutCommand command);
}
