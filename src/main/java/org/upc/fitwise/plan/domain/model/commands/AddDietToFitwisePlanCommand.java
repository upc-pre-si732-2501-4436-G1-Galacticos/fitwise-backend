package org.upc.fitwise.plan.domain.model.commands;

public record AddDietToFitwisePlanCommand(Long fitwisePlanId, Long dietId,String username) {
}
