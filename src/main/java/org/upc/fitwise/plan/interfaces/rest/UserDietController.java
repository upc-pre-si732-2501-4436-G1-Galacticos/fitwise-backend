package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsByUserIdQuery;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
import org.upc.fitwise.plan.interfaces.rest.transform.DietResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserDietController
 *
 * <p>Controller that handles the endpoints for profiles.
 * It uses the {@link DietQueryService} to users the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/users/{userId}/diets</li>
 * </ul>
 * </p>
 */


@RestController
@RequestMapping(value = "/api/v1/users/{userId}/diets", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserDietController {
    private final DietQueryService dietQueryService;
    private final DietCommandService dietCommandService;
    public UserDietController(DietQueryService dietQueryService, DietCommandService dietCommandService) {
        this.dietQueryService = dietQueryService;
        this.dietCommandService = dietCommandService;
    }

    /**
     * GET /api/v1/users/{userId}/diets
     *
     * <p>Endpoint that returns the diets for a user</p>
     *
     * @param userId the user  ID
     * @return the diets for the user
     * @see DietResource
     */
    @GetMapping
    public ResponseEntity<List<DietResource>> getDietsForUserWithUserId(@PathVariable Long userId) {
        var getAllDietsByUserIdQuery = new GetAllDietsByUserIdQuery(userId);
        var diets = dietQueryService.handle(getAllDietsByUserIdQuery);
        var DietResource = diets.stream().map(DietResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(DietResource);
    }


}