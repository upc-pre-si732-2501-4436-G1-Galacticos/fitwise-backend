package org.upc.fitwise.plan.domain.model.commands;

import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;

import java.math.BigDecimal;
import java.util.List;

public record CreateFitwisePlanCommand(String title, String note, List<String> tagNames ) {
}
