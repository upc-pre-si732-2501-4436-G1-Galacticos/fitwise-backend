package org.upc.fitwise.plan.domain.model.commands;

public record UpdateDietCommand(Long dietId, String title, String description, String username) {
}
