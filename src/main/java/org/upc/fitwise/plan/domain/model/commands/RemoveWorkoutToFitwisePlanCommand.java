package org.upc.fitwise.plan.domain.model.commands;

public record RemoveWorkoutToFitwisePlanCommand(Long fitwisePlanId, Long workoutId, String username) {
}
