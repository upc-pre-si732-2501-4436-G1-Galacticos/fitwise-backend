package org.upc.fitwise.plan.domain.model.commands;

public record RemoveDietToFitwisePlanCommand(Long fitwisePlanId, Long dietId, String username) {
}
