package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetFitwisePlansRecomendedByProfileIdQuery;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.domain.services.FitwisePlanQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.FitwisePlanResource;
import org.upc.fitwise.plan.interfaces.rest.transform.FitwisePlanResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * FitwisePlanController
 *
 * <p>Controller that handles the endpoints for profiles.
 * It uses the {@link FitwisePlanQueryService} to handle the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/profiles/{profileId}/recomended-fitwise-plans</li>
 * </ul>
 * </p>
 */


@RestController
@RequestMapping(value = "/api/v1/profiles/{profileId}/recomended-fitwise-plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles")
public class ProfileFitwisePlanController {
    private final FitwisePlanQueryService fitwisePlanQueryService;
    private final FitwisePlanCommandService fitwisePlanCommandService;


    public ProfileFitwisePlanController(FitwisePlanQueryService fitwisePlanQueryService, FitwisePlanCommandService fitwisePlanCommandService) {
        this.fitwisePlanCommandService = fitwisePlanCommandService;
        this.fitwisePlanQueryService = fitwisePlanQueryService;
    }


    /**
     * GET /api/v1/profiles/{profileId}/recomended-fitwise-plans
     *
     * <p>Endpoint that returns the recomended fitwise plans for a profile</p>
     *
     * @param profileId the profile  ID
     * @return the recomended fitwise plans for the profile
     * @see FitwisePlanResource
     */

    @GetMapping
    public ResponseEntity<List<FitwisePlanResource>> getRecomendedFitwisePlansForProfileWithProfileId(@PathVariable Long profileId) {
        var getFitwisePlansRecomendedByProfileIdQuery = new GetFitwisePlansRecomendedByProfileIdQuery(profileId);
        var  fitwisePlans = fitwisePlanQueryService.handle(getFitwisePlansRecomendedByProfileIdQuery);
        var fitwisePlanResource = fitwisePlans.stream().map(FitwisePlanResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(fitwisePlanResource);
    }


}