package org.upc.fitwise.plan.interfaces.rest.resources;

import java.util.List;

public record CreateFitwisePlanResource(String title, String description, List<String> tagNames) {
}