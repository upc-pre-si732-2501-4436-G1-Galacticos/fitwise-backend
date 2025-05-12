package org.upc.fitwise.plan.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.commands.AddDietToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.AddExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.AddWorkoutToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.CreateFitwisePlanCommand;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.domain.services.WorkoutCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.ExerciseRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class WorkoutCommandServiceImpl implements WorkoutCommandService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;


    public WorkoutCommandServiceImpl(WorkoutRepository workoutRepository,ExerciseRepository exerciseRepository){
        this.workoutRepository=workoutRepository;
        this.exerciseRepository=exerciseRepository;
    }


    @Override
    public void handle(AddExerciseToWorkoutCommand command) {
        Workout workout = workoutRepository.findById(command.workoutId())
                .orElseThrow(() -> new IllegalArgumentException("Workout does not exist"));

        Exercise exerciseToAdd  = exerciseRepository.findById(command.exerciseId())
                .orElseThrow(() -> new IllegalArgumentException("Exercise does not exist"));

        if (workout.getExercises().contains(exerciseToAdd)) {
            throw new IllegalArgumentException("This exercise is already added to the workout.");
        }

        try {
            workout.addExercise(exerciseToAdd);
            workoutRepository.save(workout);
            System.out.println("Exercise added to workout");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding  exerciseto workout: " + e.getMessage());
        }

    }







}