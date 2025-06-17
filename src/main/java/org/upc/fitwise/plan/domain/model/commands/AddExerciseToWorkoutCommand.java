package org.upc.fitwise.plan.domain.model.commands;

public record AddExerciseToWorkoutCommand(Long workoutId, Long exerciseId,String username) {
}
