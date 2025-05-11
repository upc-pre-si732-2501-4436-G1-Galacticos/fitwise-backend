package org.upc.fitwise.plan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllWorkoutsQuery;
import org.upc.fitwise.plan.domain.services.WorkoutQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
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
@Tag(name = "Workouts ", description = "Workouts Management Endpoints")
public class WorkoutsController {
    private final WorkoutQueryService workoutQueryService;



    public WorkoutsController(WorkoutQueryService workoutQueryService) {
        this.workoutQueryService = workoutQueryService;
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



}