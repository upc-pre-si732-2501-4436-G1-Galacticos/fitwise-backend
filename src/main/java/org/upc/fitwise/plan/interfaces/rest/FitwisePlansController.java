package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllFitwisePlansQuery;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetWorkoutByIdQuery;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateFitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.resources.WorkoutResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateFitwisePlanCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.FitwisePlanResourceFromEntityAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.WorkoutResourceFromEntityAssembler;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * FitwisePlans Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to fitwise-plans.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/fitwise-plans</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/fitwise-plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fitwise plans", description = "Fitwise plans Management Endpoints")
public class FitwisePlansController {
    private final FitwisePlanQueryService fitwisePlanQueryService;
    private final FitwisePlanCommandService fitwisePlanCommandService;



    public FitwisePlansController(FitwisePlanQueryService fitwisePlanQueryService, FitwisePlanCommandService fitwisePlanCommandService) {
        this.fitwisePlanQueryService = fitwisePlanQueryService;
        this.fitwisePlanCommandService= fitwisePlanCommandService;
    }


    /**
     * Gets all the fitwise plans.
     *
     * @return the list of all the FitwisePlan resources
     * @see FitwisePlanResource
     */
    @GetMapping
    public ResponseEntity<List<FitwisePlanResource>> getAllFitwisePlans() {
        var getAllFitwisePlansQuery = new GetAllFitwisePlansQuery();
        var fitwisePlans = fitwisePlanQueryService.handle(getAllFitwisePlansQuery);
        var fitwisePlanResources = fitwisePlans.stream().map(FitwisePlanResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(fitwisePlanResources);
    }


    /**
     * Creates a new FitwisePlan
     *
     * @param createFitwisePlanResource the resource containing the data for the FitwisePlan to be created
     * @return the created FitwisePlan resource
     * @see CreateFitwisePlanResource
     * @see FitwisePlanResource
     */
    @PostMapping
    public ResponseEntity<FitwisePlanResource> createFitwisePlan(@RequestBody CreateFitwisePlanResource createFitwisePlanResource) {
        var createFitwisePlanCommand = CreateFitwisePlanCommandFromResourceAssembler.toCommandFromResource(createFitwisePlanResource);
        var fitwisePlanId = fitwisePlanCommandService.handle(createFitwisePlanCommand);
        if (fitwisePlanId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getFitwisePlanByIdQuery = new GetFitwisePlanByIdQuery(fitwisePlanId);
        var fitwisePlan = fitwisePlanQueryService.handle(getFitwisePlanByIdQuery);
        if (fitwisePlan.isEmpty()) return ResponseEntity.notFound().build();
        var fitwisePlanResource = FitwisePlanResourceFromEntityAssembler.toResourceFromEntity(fitwisePlan.get());
        return new ResponseEntity<>(fitwisePlanResource, HttpStatus.CREATED);
    }



    /**
     * Gets a FitwisePlan by its id.
     *
     * @param fitwisePlanId the id of the fitwisePlan to be retrieved
     * @return the fitwisePlan resource with the given id
     * @see FitwisePlanResource
     */
    @GetMapping("/{fitwisePlanId}")
    public ResponseEntity<FitwisePlanResource> getFitwisePlanById(@PathVariable Long fitwisePlanId) {
        var getFitwisePlanByIdQuery = new GetFitwisePlanByIdQuery(fitwisePlanId);
        var fitwisePlan = fitwisePlanQueryService.handle(getFitwisePlanByIdQuery);
        if (fitwisePlan.isEmpty()) return ResponseEntity.badRequest().build();
        var fitwisePlanResource = FitwisePlanResourceFromEntityAssembler.toResourceFromEntity(fitwisePlan.get());
        return ResponseEntity.ok(fitwisePlanResource);
    }

}