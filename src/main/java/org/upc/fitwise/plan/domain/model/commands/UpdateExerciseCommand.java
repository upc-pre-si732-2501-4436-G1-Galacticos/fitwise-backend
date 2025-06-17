package org.upc.fitwise.plan.domain.model.commands;

public record UpdateExerciseCommand(Long exerciseId,String title,String description,String username) {
}
