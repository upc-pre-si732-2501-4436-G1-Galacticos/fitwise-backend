package org.upc.fitwise.plan.domain.model.commands;

public record AddMealToDietCommand(Long dietId, Long mealId) {
}
