package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesByUserIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetExerciseByIdQuery;
import org.upc.fitwise.plan.domain.services.ExerciseCommandService;
import org.upc.fitwise.plan.domain.services.ExerciseQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.resources.ExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateExerciseCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.ExerciseResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserExerciseController
 *
 * <p>Controller that handles the endpoints for profiles.
 * It uses the {@link ExerciseQueryService} to users the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/users/{userId}/exercises</li>
 * </ul>
 * </p>
 */


@RestController
@RequestMapping(value = "/api/v1/users/{userId}/exercises", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserExerciseController {
    private final ExerciseQueryService exerciseQueryService;
    private final ExerciseCommandService exerciseCommandService;
    public UserExerciseController(ExerciseQueryService exerciseQueryService, ExerciseCommandService exerciseCommandService) {
        this.exerciseQueryService = exerciseQueryService;
        this.exerciseCommandService = exerciseCommandService;
    }

    /**
     * GET /api/v1/users/{userId}/exercises
     *
     * <p>Endpoint that returns the exercises for a user</p>
     *
     * @param userId the user  ID
     * @return the exercises for the user
     * @see ExerciseResource
     */
    @GetMapping
    public ResponseEntity<List<ExerciseResource>> getExercisesForUserWithUserId(@PathVariable Long userId) {
        var getAllExercisesByUserIdQuery = new GetAllExercisesByUserIdQuery(userId);
        var exercises = exerciseQueryService.handle(getAllExercisesByUserIdQuery);
        var exerciseResource = exercises.stream().map(ExerciseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(exerciseResource);
    }




}