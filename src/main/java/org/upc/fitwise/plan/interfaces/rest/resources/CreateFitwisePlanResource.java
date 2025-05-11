package org.upc.fitwise.plan.interfaces.rest.resources;

import java.util.List;

public record CreateFitwisePlanResource(String title, String note, List<String> tagNames) {
}