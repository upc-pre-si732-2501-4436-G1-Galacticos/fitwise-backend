package org.upc.fitwise.plan.interfaces.rest.resources;


import org.upc.fitwise.plan.domain.model.aggregates.Exercise;

import java.util.List;

public record WorkoutResource(Long id, String title, String note, List<Exercise> exercises)  {
}

