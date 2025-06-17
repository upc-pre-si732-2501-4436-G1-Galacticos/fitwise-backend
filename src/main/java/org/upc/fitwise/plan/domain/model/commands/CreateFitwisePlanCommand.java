package org.upc.fitwise.plan.domain.model.commands;


import java.util.List;

public record CreateFitwisePlanCommand(String title, String note, List<String> tagNames ) {
}
