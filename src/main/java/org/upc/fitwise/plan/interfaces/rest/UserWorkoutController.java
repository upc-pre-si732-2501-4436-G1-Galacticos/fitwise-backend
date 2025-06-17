package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsByUserIdQuery;
import org.upc.fitwise.plan.domain.services.WorkoutCommandService;
import org.upc.fitwise.plan.domain.services.WorkoutQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.transform.WorkoutResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserWorkoutController
 *
 * <p>Controller that handles the endpoints for profiles.
 * It uses the {@link WorkoutQueryService} to users the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/users/{userId}/workouts</li>
 * </ul>
 * </p>
 */


@RestController
@RequestMapping(value = "/api/v1/users/{userId}/workouts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserWorkoutController {
    private final WorkoutQueryService workoutQueryService;
    private final WorkoutCommandService workoutCommandService;
    public UserWorkoutController(WorkoutQueryService workoutQueryService, WorkoutCommandService workoutCommandService) {
        this.workoutQueryService = workoutQueryService;
        this.workoutCommandService = workoutCommandService;
    }

    /**
     * GET /api/v1/users/{userId}/workouts
     *
     * <p>Endpoint that returns the workouts for a user</p>
     *
     * @param userId the user  ID
     * @return the workouts for the user
     * @see WorkoutResource
     */
    @GetMapping
    public ResponseEntity<List<WorkoutResource>> getWorkoutsForUserWithUserId(@PathVariable Long userId) {
        var getAllWorkoutsByUserIdQuery = new GetAllWorkoutsByUserIdQuery(userId);
        var workouts = workoutQueryService.handle(getAllWorkoutsByUserIdQuery);
        var WorkoutResource = workouts.stream().map(WorkoutResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(WorkoutResource);
    }




}