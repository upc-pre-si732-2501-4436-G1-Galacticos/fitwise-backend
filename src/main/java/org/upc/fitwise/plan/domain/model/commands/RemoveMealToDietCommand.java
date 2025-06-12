package org.upc.fitwise.plan.domain.model.commands;

public record RemoveMealToDietCommand(Long dietId, Long mealId, String username) {
}
