package org.upc.fitwise.plan.domain.services;

import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.commands.CreateExerciseCommand;
import org.upc.fitwise.plan.domain.model.commands.UpdateExerciseCommand;

import java.util.Optional;

public interface ExerciseCommandService {
    Long handle(CreateExerciseCommand command);
    Optional<Exercise> handle(UpdateExerciseCommand command);
}
