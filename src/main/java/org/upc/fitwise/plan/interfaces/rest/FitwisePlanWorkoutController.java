package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.commands.AddWorkoutToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByWorkoutIdQuery;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.transform.FitwisePlanResourceFromEntityAssembler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing workouts of a Fitwise plans.
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /fitwise-plans/{fitwisePlanId}/workouts/{workoutId}: Adds a workout to the fitwise plans</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/fitwise-plans/{fitwisePlanId}/workouts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fitwise plans")
public class FitwisePlanWorkoutController {
    private final FitwisePlanQueryService fitwisePlanQueryService;
    private final FitwisePlanCommandService fitwisePlanCommandService;

    public FitwisePlanWorkoutController(FitwisePlanQueryService fitwisePlanQueryService, FitwisePlanCommandService fitwisePlanCommandService) {
        this.fitwisePlanQueryService = fitwisePlanQueryService;
        this.fitwisePlanCommandService= fitwisePlanCommandService;
    }

    /**
     * Adds a workout to the fitwisePlan.
     * @param fitwisePlanId The Fitwise plan identifier.
     * @param workoutId The workout identifier.
     * @return The fitwisePlan item.
     * @see FitwisePlanResource
     */
    @PostMapping("{workoutId}")
    public ResponseEntity<FitwisePlanResource> addWorkoutToFitwisePlan(@PathVariable Long fitwisePlanId, @PathVariable Long workoutId) {
        fitwisePlanCommandService.handle(new AddWorkoutToFitwisePlanCommand(fitwisePlanId, workoutId));
        var getFitwisePlanByWorkoutIdQuery = new GetFitwisePlanByWorkoutIdQuery(workoutId);
        var fitwisePlan = fitwisePlanQueryService.handle(getFitwisePlanByWorkoutIdQuery);
        if (fitwisePlan.isEmpty()) return ResponseEntity.notFound().build();
        var fitwisePlanResource = FitwisePlanResourceFromEntityAssembler.toResourceFromEntity(fitwisePlan.get());
        return ResponseEntity.ok(fitwisePlanResource);
    }

}