package org.upc.fitwise.plan.interfaces.rest;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllMealsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetMealByIdQuery;
import org.upc.fitwise.plan.domain.services.MealCommandService;
import org.upc.fitwise.plan.domain.services.MealQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateMealResource;
import org.upc.fitwise.plan.interfaces.rest.resources.MealResource;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateMealResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateMealCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.MealResourceFromEntityAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.UpdateMealCommandFromResourceAssembler;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Meals Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to Meals.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/meals</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/meals", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Meals", description = "Meals Management Endpoints")
public class MealsController {
    private final MealQueryService mealQueryService;
    private final MealCommandService mealCommandService;

    public MealsController(MealQueryService mealQueryService, MealCommandService mealCommandService) {
        this.mealQueryService = mealQueryService;
        this.mealCommandService=mealCommandService;
    }


    /**
     * Gets all the Meals.
     *
     * @return the list of all the Meals resources
     * @see MealResource
     */
    @GetMapping
    public ResponseEntity<List<MealResource>> getAllMeals() {
        var getAllMealsQuery = new GetAllMealsQuery();
        var meals = mealQueryService.handle(getAllMealsQuery);
        var mealResource = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(mealResource);
    }


    /**
     * Gets a meals by its id.
     *
     * @param mealId the id of the Meal to be retrieved
     * @return the meal resource with the given id
     * @see MealResource
     */
    @GetMapping("/{mealId}")
    public ResponseEntity<MealResource> getMealById(@PathVariable Long mealId) {
        var getMealByIdQuery = new GetMealByIdQuery(mealId);
        var meal = mealQueryService.handle(getMealByIdQuery);
        if (meal.isEmpty()) return ResponseEntity.notFound().build();
        var mealResource = MealResourceFromEntityAssembler.toResourceFromEntity(meal.get());
        return ResponseEntity.ok(mealResource);
    }

    /**
     * Creates a new meal.
     *
     * @param createMealResource the resource containing the data for the Meal to be created
     * @return the created Meal resource
     * @see CreateMealResource
     * @see MealResource
     */
    @PostMapping
    public ResponseEntity<MealResource> createMeal(@RequestBody CreateMealResource createMealResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var createMealCommand = CreateMealCommandFromResourceAssembler.toCommandFromResource(createMealResource,username);
        var mealId = mealCommandService.handle(createMealCommand);
        if (mealId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getMealByIdQuery = new GetMealByIdQuery(mealId);
        var meal = mealQueryService.handle(getMealByIdQuery);
        if (meal.isEmpty()) return ResponseEntity.badRequest().build();
        var mealResource = MealResourceFromEntityAssembler.toResourceFromEntity(meal.get());
        return new ResponseEntity<>(mealResource, HttpStatus.CREATED);
    }

    /**
     * Updates a meal.
     *
     * @param mealId the id of the Meal to be updated
     * @param updateMealResource the resource containing the data for the Meal to be updated
     * @return the updated Meal resource
     * @see UpdateMealResource
     * @see MealResource
     */
    @PutMapping("/{mealId}")
    public ResponseEntity<MealResource> updateMeal(@PathVariable Long mealId, @RequestBody UpdateMealResource updateMealResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var updateMealCommand = UpdateMealCommandFromResourceAssembler.toCommandFromResource(mealId, updateMealResource,username);
        var updatedMeal = mealCommandService.handle(updateMealCommand);
        if (updatedMeal.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var mealResource = MealResourceFromEntityAssembler.toResourceFromEntity(updatedMeal.get());
        return ResponseEntity.ok(mealResource);
    }
    

}