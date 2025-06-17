package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesByUserIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesQuery;
import org.upc.fitwise.plan.domain.model.queries.GetExerciseByIdQuery;
import org.upc.fitwise.plan.domain.services.ExerciseQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.ExerciseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseQueryServiceImpl implements ExerciseQueryService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseQueryServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> handle(GetAllExercisesQuery query) {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> handle(GetExerciseByIdQuery query) {
        return exerciseRepository.findById(query.exerciseId());
    }

    @Override
    public List<Exercise> handle(GetAllExercisesByUserIdQuery query) {
        return exerciseRepository.findAllByUserId(query.userId());
    }




}
