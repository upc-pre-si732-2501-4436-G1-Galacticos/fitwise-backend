package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.commands.AddExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.commands.RemoveExerciseToWorkoutCommand;
import org.upc.fitwise.plan.domain.model.queries.GetWorkoutByIdQuery;
import org.upc.fitwise.plan.domain.services.WorkoutCommandService;
import org.upc.fitwise.plan.domain.services.WorkoutQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.ExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.transform.ExerciseResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing exercises of a Workout .
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /workouts/{workoutId}/exercises/{exerciseId}: Adds a exercises to the Workouts</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/workouts/{workoutId}/exercises", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Workouts")
public class WorkoutExerciseController {
    private final WorkoutQueryService workoutQueryService;
    private final WorkoutCommandService workoutCommandService;

    public WorkoutExerciseController(WorkoutQueryService workoutQueryService,WorkoutCommandService workoutCommandService) {
        this.workoutQueryService = workoutQueryService;
        this.workoutCommandService=workoutCommandService;
    }

    /**
     * Adds a exercises to the workout.
     * @param workoutId The workout identifier.
     * @param exerciseId The exercise identifier.
     * @return the exercises for the workout
     * @see ExerciseResource
     */
    @PostMapping("{exerciseId}")
    public ResponseEntity<List<ExerciseResource>> addExerciseToWorkout(@PathVariable Long workoutId, @PathVariable Long exerciseId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        workoutCommandService.handle(new AddExerciseToWorkoutCommand(workoutId, exerciseId,username));
        var getWorkoutByIdQuery = new GetWorkoutByIdQuery(workoutId);
        var workout = workoutQueryService.handle(getWorkoutByIdQuery);
        if (workout.isEmpty()) return ResponseEntity.notFound().build();
        var exerciseResources = workout.get().getExercises().stream().map(ExerciseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(exerciseResources);
    }

    /**
     * Remove a exercises to the workout.
     * @param workoutId The workout identifier.
     * @param exerciseId The exercise identifier.
     * @return the exercises for the workout
     * @see ExerciseResource
     */
    @DeleteMapping("{exerciseId}")
    public ResponseEntity<List<ExerciseResource>> removeExerciseToWorkout(@PathVariable Long workoutId, @PathVariable Long exerciseId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        workoutCommandService.handle(new RemoveExerciseToWorkoutCommand(workoutId, exerciseId,username));
        var getWorkoutByIdQuery = new GetWorkoutByIdQuery(workoutId);
        var workout = workoutQueryService.handle(getWorkoutByIdQuery);
        if (workout.isEmpty()) return ResponseEntity.notFound().build();
        var exerciseResources = workout.get().getExercises().stream().map(ExerciseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(exerciseResources);
    }

}