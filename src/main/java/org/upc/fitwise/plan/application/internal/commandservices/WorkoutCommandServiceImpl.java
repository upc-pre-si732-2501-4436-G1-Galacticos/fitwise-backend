package org.upc.fitwise.plan.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.exceptions.*;
import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.commands.*;
import org.upc.fitwise.plan.domain.services.WorkoutCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.ExerciseRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import java.util.Optional;


@Service
public class WorkoutCommandServiceImpl implements WorkoutCommandService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExternalIamService externalIamService;


    public WorkoutCommandServiceImpl(WorkoutRepository workoutRepository,ExerciseRepository exerciseRepository,ExternalIamService externalIamService){
        this.workoutRepository=workoutRepository;
        this.exerciseRepository=exerciseRepository;
        this.externalIamService= externalIamService;
    }


    @Override
    public void handle(AddExerciseToWorkoutCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedWorkoutAccessException("User not found or unauthorized for this operation."));

        Workout workout = workoutRepository.findById(command.workoutId())
                .orElseThrow(() -> new WorkoutNotFoundException(command.workoutId()));

        if (!userId.equals(workout.getUserId())) {
            throw new UnauthorizedWorkoutAccessException("Add not permitted: Workout belongs to a different user");
        }
        Exercise exerciseToAdd  = exerciseRepository.findById(command.exerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(command.exerciseId()));

        if (workout.getExercises().contains(exerciseToAdd)) {
            throw new ExerciseAlreadyAddedToWorkoutException();
        }

        workout.addExerciseToWorkout(exerciseToAdd);
        workoutRepository.save(workout);
        System.out.println("Exercise added to workout");

    }


    @Override
    public void handle(RemoveExerciseToWorkoutCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedWorkoutAccessException("User not found or unauthorized for this operation."));

        Workout workout = workoutRepository.findById(command.workoutId())
                .orElseThrow(() -> new WorkoutNotFoundException(command.workoutId()));

        if (!userId.equals(workout.getUserId())) {
            throw new UnauthorizedWorkoutAccessException("Remove not permitted: Workout belongs to a different user");
        }
        Exercise exerciseToRemove  = exerciseRepository.findById(command.exerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(command.exerciseId()));

        if (!workout.getExercises().contains(exerciseToRemove)) {
            throw new ExerciseNotAddedToWorkoutException();
        }

        workout.removeExerciseToWorkout(exerciseToRemove);
        workoutRepository.save(workout);
        System.out.println("Exercise Removed from workout");

    }


    @Override
    public Long handle(CreateWorkoutCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedWorkoutAccessException("User not found or unauthorized for this operation."));

        if (workoutRepository.existsByTitle(command.title())) {
            throw new WorkoutAlreadyExistsException(command.title());
        }

        var workout = new Workout(command.title(), command.description(), userId);

        workoutRepository.save(workout);
        return workout.getId();
    }

    @Override
    public Optional<Workout> handle(UpdateWorkoutCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedWorkoutAccessException("User not found or unauthorized for this operation."));

        if (workoutRepository.existsByTitleAndIdIsNot(command.title(), command.workoutId())) {
            throw new WorkoutAlreadyExistsException(command.title());
        }

        var result = workoutRepository.findById(command.workoutId());
        if (result.isEmpty()) {
            throw new WorkoutNotFoundException(command.workoutId());
        }

        var workoutToUpdate = result.get();
        if (!userId.equals(workoutToUpdate.getUserId())) {
            throw new UnauthorizedWorkoutAccessException("Update not permitted: Workout belongs to a different user");
        }

        var updatedWorkout = workoutRepository.save(workoutToUpdate.updateInformation(command.title(), command.description()));
        return Optional.of(updatedWorkout);
    }







}