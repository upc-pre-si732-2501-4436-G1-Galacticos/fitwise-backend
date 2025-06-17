package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsByUserIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetMealByIdQuery;
import org.upc.fitwise.plan.domain.services.MealQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.MealRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MealQueryServiceImpl implements MealQueryService {

    private final MealRepository mealRepository;

    public MealQueryServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public List<Meal> handle(GetAllMealsQuery query) {
        return mealRepository.findAll();
    }

    @Override
    public Optional<Meal> handle(GetMealByIdQuery query) {
        return mealRepository.findById(query.mealId());
    }

    @Override
    public List<Meal> handle(GetAllMealsByUserIdQuery query) {
        return mealRepository.findAllByUserId(query.userId());
    }




}
