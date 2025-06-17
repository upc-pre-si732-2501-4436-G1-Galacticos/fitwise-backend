package org.upc.fitwise.plan.domain.model.commands;

public record RemoveExerciseToWorkoutCommand(Long workoutId, Long exerciseId, String username) {
}
