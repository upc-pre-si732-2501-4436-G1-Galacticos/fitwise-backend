package org.upc.fitwise.plan.interfaces.rest;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllExercisesQuery;
import org.upc.fitwise.plan.domain.model.queries.GetExerciseByIdQuery;
import org.upc.fitwise.plan.domain.services.ExerciseCommandService;
import org.upc.fitwise.plan.domain.services.ExerciseQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.resources.ExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateExerciseResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateExerciseCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.ExerciseResourceFromEntityAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.UpdateExerciseCommandFromResourceAssembler;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Exercises Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to Exercises.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/exercises</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/exercises", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Exercises", description = "Exercises Management Endpoints")
public class ExercisesController {
    private final ExerciseQueryService exerciseQueryService;
    private final ExerciseCommandService exerciseCommandService;

    public ExercisesController(ExerciseQueryService exerciseQueryService,ExerciseCommandService exerciseCommandService) {
        this.exerciseQueryService = exerciseQueryService;
        this.exerciseCommandService=exerciseCommandService;
    }


    /**
     * Gets all the Exercises.
     *
     * @return the list of all the Exercises resources
     * @see ExerciseResource
     */
    @GetMapping
    public ResponseEntity<List<ExerciseResource>> getAllExercises() {
        var getAllExercisesQuery = new GetAllExercisesQuery();
        var exercises = exerciseQueryService.handle(getAllExercisesQuery);
        var exerciseResource = exercises.stream().map(ExerciseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(exerciseResource);
    }


    /**
     * Gets a exercise by its id.
     *
     * @param exerciseId the id of the exercise to be retrieved
     * @return the exercise resource with the given id
     * @see ExerciseResource
     */
    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResource> getExerciseById(@PathVariable Long exerciseId) {
        var getExerciseByIdQuery = new GetExerciseByIdQuery(exerciseId);
        var exercise = exerciseQueryService.handle(getExerciseByIdQuery);
        if (exercise.isEmpty()) return ResponseEntity.notFound().build();
        var exerciseResource = ExerciseResourceFromEntityAssembler.toResourceFromEntity(exercise.get());
        return ResponseEntity.ok(exerciseResource);
    }

    /**
     * Creates a new exercise.
     *
     * @param createExerciseResource the resource containing the data for the exercise to be created
     * @return the created exercise resource
     * @see CreateExerciseResource
     * @see ExerciseResource
     */
    @PostMapping
    public ResponseEntity<ExerciseResource> createExercise(@RequestBody CreateExerciseResource createExerciseResource,@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var createExerciseCommand = CreateExerciseCommandFromResourceAssembler.toCommandFromResource(createExerciseResource,username);
        var exerciseId = exerciseCommandService.handle(createExerciseCommand);
        if (exerciseId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getExerciseByIdQuery = new GetExerciseByIdQuery(exerciseId);
        var exercise = exerciseQueryService.handle(getExerciseByIdQuery);
        if (exercise.isEmpty()) return ResponseEntity.badRequest().build();
        var exerciseResource = ExerciseResourceFromEntityAssembler.toResourceFromEntity(exercise.get());
        return new ResponseEntity<>(exerciseResource, HttpStatus.CREATED);
    }

    /**
     * Updates a exercise.
     *
     * @param exerciseId             the id of the exercise to be updated
     * @param updateExerciseResource the resource containing the data for the exercise to be updated
     * @return the updated Exercise resource
     * @see UpdateExerciseResource
     * @see ExerciseResource
     */
    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResource> updateExercise(@PathVariable Long exerciseId, @RequestBody UpdateExerciseResource updateExerciseResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var updateExerciseCommand = UpdateExerciseCommandFromResourceAssembler.toCommandFromResource(exerciseId, updateExerciseResource,username);
        var updatedExercise = exerciseCommandService.handle(updateExerciseCommand);
        if (updatedExercise.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var ExerciseResource = ExerciseResourceFromEntityAssembler.toResourceFromEntity(updatedExercise.get());
        return ResponseEntity.ok(ExerciseResource);
    }
    


}