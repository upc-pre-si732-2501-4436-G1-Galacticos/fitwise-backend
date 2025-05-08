package org.upc.fitwise.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.model.aggregates.Goal;
import org.upc.fitwise.profiles.domain.model.queries.GetAllGoalsQuery;
import org.upc.fitwise.profiles.domain.services.GoalQueryService;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.GoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GoalQueryServiceImpl implements GoalQueryService {

    private final GoalRepository goalRepository;

    public GoalQueryServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }


    @Override
    public List<Goal> handle(GetAllGoalsQuery query) {
        return goalRepository.findAll();
    }


}
