package org.upc.fitwise.plan.domain.model.commands;

public record UpdateMealCommand(Long mealId, String title, String description, String username) {
}
