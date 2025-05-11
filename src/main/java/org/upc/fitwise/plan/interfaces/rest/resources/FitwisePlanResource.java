package org.upc.fitwise.plan.interfaces.rest.resources;

import lombok.Getter;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;


import java.util.List;


public record FitwisePlanResource(Long id,String title, Long dietId, Long workoutId, String note, List<String>  tagNames) {
}

