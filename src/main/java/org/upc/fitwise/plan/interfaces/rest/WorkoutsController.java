package org.upc.fitwise.plan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetWorkoutByIdQuery;
import org.upc.fitwise.plan.domain.services.WorkoutCommandService;
import org.upc.fitwise.plan.domain.services.WorkoutQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateWorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateWorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateWorkoutCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.UpdateWorkoutCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.WorkoutResourceFromEntityAssembler;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Workouts Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to workoutss.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/workouts</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/workouts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Workouts", description = "Workouts Management Endpoints")
public class WorkoutsController {
    private final WorkoutQueryService workoutQueryService;
    private final WorkoutCommandService workoutCommandService;



    public WorkoutsController(WorkoutQueryService workoutQueryService,WorkoutCommandService workoutCommandService) {
        this.workoutQueryService = workoutQueryService;
        this.workoutCommandService=workoutCommandService;
    }

    /**
     * Gets all the workouts.
     *
     * @return the list of all the workouts resources
     * @see WorkoutResource
     */
    @GetMapping
    public ResponseEntity<List<WorkoutResource>> getAllWorkouts() {
        var getAllWorkoutsQuery = new GetAllWorkoutsQuery();
        var workouts = workoutQueryService.handle(getAllWorkoutsQuery);
        var workoutResource = workouts.stream().map(WorkoutResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(workoutResource);
    }

    /**
     * Gets a workout by its id.
     *
     * @param workoutId the id of the workout to be retrieved
     * @return the workout resource with the given id
     * @see WorkoutResource
     */
    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResource> getWorkoutById(@PathVariable Long workoutId) {
        var getWorkoutByIdQuery = new GetWorkoutByIdQuery(workoutId);
        var workout = workoutQueryService.handle(getWorkoutByIdQuery);
            if (workout.isEmpty()) return ResponseEntity.badRequest().build();
        var workoutResource = WorkoutResourceFromEntityAssembler.toResourceFromEntity(workout.get());
        return ResponseEntity.ok(workoutResource);
    }


    /**
     * Creates a new workout.
     *
     * @param createWorkoutResource the resource containing the data for the workout to be created
     * @return the created workout resource
     * @see CreateWorkoutResource
     * @see WorkoutResource
     */
    @PostMapping
    public ResponseEntity<WorkoutResource> createWorkout(@RequestBody CreateWorkoutResource createWorkoutResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var createWorkoutCommand = CreateWorkoutCommandFromResourceAssembler.toCommandFromResource(createWorkoutResource,username);
        var workoutId = workoutCommandService.handle(createWorkoutCommand);
        if (workoutId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getWorkoutByIdQuery = new GetWorkoutByIdQuery(workoutId);
        var workout = workoutQueryService.handle(getWorkoutByIdQuery);
        if (workout.isEmpty()) return ResponseEntity.badRequest().build();
        var WorkoutResource = WorkoutResourceFromEntityAssembler.toResourceFromEntity(workout.get());
        return new ResponseEntity<>(WorkoutResource, HttpStatus.CREATED);
    }

    /**
     * Updates a workout.
     *
     * @param workoutId the id of the workout to be updated
     * @param updateWorkoutResource the resource containing the data for the Workout to be updated
     * @return the updated Workout resource
     * @see UpdateWorkoutResource
     * @see WorkoutResource
     */
    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutResource> updateWorkout(@PathVariable Long workoutId, @RequestBody UpdateWorkoutResource updateWorkoutResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var updateWorkoutCommand = UpdateWorkoutCommandFromResourceAssembler.toCommandFromResource(workoutId, updateWorkoutResource,username);
        var updatedWorkout = workoutCommandService.handle(updateWorkoutCommand);
        if (updatedWorkout.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var workoutResource = WorkoutResourceFromEntityAssembler.toResourceFromEntity(updatedWorkout.get());
        return ResponseEntity.ok(workoutResource);
    }



}