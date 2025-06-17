package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.commands.AddDietToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.RemoveDietToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByDietIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlanByIdQuery;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.transform.FitwisePlanResourceFromEntityAssembler;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing diets of a Fitwise plans.
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /fitwise-plans/{fitwisePlanId}/diets/{dietId}: Adds a diet to the fitwise plans</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/fitwise-plans/{fitwisePlanId}/diets", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fitwise plans")
public class FitwisePlanDietController {
    private final FitwisePlanQueryService fitwisePlanQueryService;
    private final FitwisePlanCommandService fitwisePlanCommandService;

    public FitwisePlanDietController(FitwisePlanQueryService fitwisePlanQueryService, FitwisePlanCommandService fitwisePlanCommandService) {
        this.fitwisePlanQueryService = fitwisePlanQueryService;
        this.fitwisePlanCommandService= fitwisePlanCommandService;
    }

    /**
     * Adds a diet to the fitwisePlan.
     * @param fitwisePlanId The Fitwise plan identifier.
     * @param dietId The diet identifier.
     * @return The fitwisePlan item.
     * @see FitwisePlanResource
     */
    @PostMapping("{dietId}")
    public ResponseEntity<FitwisePlanResource> addDietToFitwisePlan(@PathVariable Long fitwisePlanId, @PathVariable Long dietId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        fitwisePlanCommandService.handle(new AddDietToFitwisePlanCommand(fitwisePlanId, dietId,username));
        var getFitwisePlanByIdQuery = new GetFitwisePlanByIdQuery(fitwisePlanId);
        var fitwisePlan = fitwisePlanQueryService.handle(getFitwisePlanByIdQuery);
        if (fitwisePlan.isEmpty()) return ResponseEntity.badRequest().build();
        var fitwisePlanResource = FitwisePlanResourceFromEntityAssembler.toResourceFromEntity(fitwisePlan.get());
        return ResponseEntity.ok(fitwisePlanResource);
    }


    /**
     * Remove a diet to the fitwisePlan.
     * @param fitwisePlanId The fitwisePlan identifier.
     * @param dietId The diet identifier.
     * @return the fitwisePlan
     * @see FitwisePlanResource
     */
    @DeleteMapping("{dietId}")
    public ResponseEntity<FitwisePlanResource>removeDietToFitwisePlan(@PathVariable Long fitwisePlanId, @PathVariable Long dietId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        fitwisePlanCommandService.handle(new RemoveDietToFitwisePlanCommand(fitwisePlanId, dietId,username));
        var getFitwisePlanByIdQuery = new GetFitwisePlanByIdQuery(fitwisePlanId);
        var fitwisePlan = fitwisePlanQueryService.handle(getFitwisePlanByIdQuery);
        if (fitwisePlan.isEmpty()) return ResponseEntity.badRequest().build();
        var fitwisePlanResource = FitwisePlanResourceFromEntityAssembler.toResourceFromEntity(fitwisePlan.get());
        return ResponseEntity.ok(fitwisePlanResource);
    }

}