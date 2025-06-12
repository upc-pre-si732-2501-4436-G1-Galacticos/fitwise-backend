package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.commands.AddMealToDietCommand;
import org.upc.fitwise.plan.domain.model.commands.RemoveMealToDietCommand;
import org.upc.fitwise.plan.domain.model.queries.GetDietByIdQuery;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.MealResource;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
import org.upc.fitwise.plan.interfaces.rest.transform.MealResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing meals of a Diet .
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /diets/{dietId}/meals/{mealId}: Adds a meals to the Diets</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/diets/{dietId}/meals", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diets")
public class DietMealController {
    private final DietQueryService dietQueryService;
    private final DietCommandService dietCommandService;

    public DietMealController(DietQueryService dietQueryService,DietCommandService dietCommandService) {
        this.dietQueryService = dietQueryService;
        this.dietCommandService=dietCommandService;
    }

    /**
     * Adds a meals to the diet.
     * @param dietId The diet identifier.
     * @param mealId The meal identifier.
     * @return the meals for the diet
     * @see MealResource
     */
    @PostMapping("{mealId}")
    public ResponseEntity<List<MealResource>> addMealToDiet(@PathVariable Long dietId, @PathVariable Long mealId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        dietCommandService.handle(new AddMealToDietCommand(dietId, mealId,username));
        var getDietByIdQuery = new GetDietByIdQuery(dietId);
        var diet = dietQueryService.handle(getDietByIdQuery);
        if (diet.isEmpty()) return ResponseEntity.notFound().build();
        var mealResources = diet.get().getMeals().stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(mealResources);
    }

    /**
     * Remove a meals to the diet.
     * @param dietId The diet identifier.
     * @param mealId The meal identifier.
     * @return the meals for the diet
     * @see MealResource
     */
    @DeleteMapping("{mealId}")
    public ResponseEntity<List<MealResource>> removeMealToDiet(@PathVariable Long dietId, @PathVariable Long mealId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        dietCommandService.handle(new RemoveMealToDietCommand(dietId, mealId,username));
        var getDietByIdQuery = new GetDietByIdQuery(dietId);
        var diet = dietQueryService.handle(getDietByIdQuery);
        if (diet.isEmpty()) return ResponseEntity.notFound().build();
        var mealResources = diet.get().getMeals().stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(mealResources);
    }

}