package org.upc.fitwise.plan.interfaces.rest.resources;


import org.upc.fitwise.plan.domain.model.aggregates.Meal;

import java.util.List;

public record DietResource(Long id, String title, String description, List<Meal> meals) {
}

