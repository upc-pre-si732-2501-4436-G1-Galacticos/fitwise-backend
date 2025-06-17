package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsByUserIdQuery;
import org.upc.fitwise.plan.domain.services.MealCommandService;
import org.upc.fitwise.plan.domain.services.MealQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.MealResource;
import org.upc.fitwise.plan.interfaces.rest.transform.MealResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserMealController
 *
 * <p>Controller that handles the endpoints for profiles.
 * It uses the {@link MealQueryService} to users the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/users/{userId}/meals</li>
 * </ul>
 * </p>
 */


@RestController
@RequestMapping(value = "/api/v1/users/{userId}/meals", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserMealController {
    private final MealQueryService mealQueryService;
    private final MealCommandService mealCommandService;
    public UserMealController(MealQueryService mealQueryService, MealCommandService mealCommandService) {
        this.mealQueryService = mealQueryService;
        this.mealCommandService = mealCommandService;
    }

    /**
     * GET /api/v1/users/{userId}/meals
     *
     * <p>Endpoint that returns the meals for a user</p>
     *
     * @param userId the user  ID
     * @return the meals for the user
     * @see MealResource
     */
    @GetMapping
    public ResponseEntity<List<MealResource>> getMealsForUserWithUserId(@PathVariable Long userId) {
        var getAllMealsByUserIdQuery = new GetAllMealsByUserIdQuery(userId);
        var meals = mealQueryService.handle(getAllMealsByUserIdQuery);
        var mealResource = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(mealResource);
    }




}