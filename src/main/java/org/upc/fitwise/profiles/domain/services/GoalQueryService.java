package org.upc.fitwise.profiles.domain.services;


import org.upc.fitwise.profiles.domain.model.aggregates.Goal;
import org.upc.fitwise.profiles.domain.model.queries.GetAllGoalsQuery;

import java.util.List;
import java.util.Optional;

public interface GoalQueryService {
    List<Goal> handle(GetAllGoalsQuery query);
}
