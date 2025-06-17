package org.upc.fitwise.plan.domain.model.commands;

public record UpdateWorkoutCommand(Long workoutId, String title, String description, String username) {
}
