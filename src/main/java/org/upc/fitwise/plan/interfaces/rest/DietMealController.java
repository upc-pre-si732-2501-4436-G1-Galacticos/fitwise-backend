package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.commands.AddMealToDietCommand;
import org.upc.fitwise.plan.domain.model.queries.GetDietByIdQuery;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.MealResource;
import org.upc.fitwise.plan.interfaces.rest.transform.MealResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing meals of a Diet.
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /diets/{dietId}/meals/{mealId}: Adds a meal to the diet</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/diets/{dietId}/meals", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diets")
public class DietMealController {
    private final DietQueryService dietQueryService;
    private final DietCommandService dietCommandService;
    public DietMealController(DietQueryService dietQueryService,DietCommandService dietCommandService) {
        this.dietQueryService = dietQueryService;
        this.dietCommandService = dietCommandService;
    }

    /**
     * Adds a meal to the diet.
     * @param dietId The diet identifier.
     * @param mealId The meal identifier.
     * @return the meals for the diet
     * @see MealResource
     */
    @PostMapping("{mealId}")
    public ResponseEntity<List<MealResource>> addMealToDiet(@PathVariable Long dietId, @PathVariable Long mealId) {
        dietCommandService.handle(new AddMealToDietCommand(dietId, mealId));
        var getDietByIdQuery = new GetDietByIdQuery(dietId);
        var diet = dietQueryService.handle(getDietByIdQuery);
        if (diet.isEmpty()) return ResponseEntity.notFound().build();
        var mealResources = diet.get().getMeals().stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(mealResources);
    }

}