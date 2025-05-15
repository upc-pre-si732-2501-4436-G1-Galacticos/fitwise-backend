package org.upc.fitwise.plan.domain.model.commands;

public record AddWorkoutToFitwisePlanCommand(Long fitwisePlanId, Long workoutId) {
}
