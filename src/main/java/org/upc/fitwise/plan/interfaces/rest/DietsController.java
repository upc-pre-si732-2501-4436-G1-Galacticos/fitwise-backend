package org.upc.fitwise.plan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetDietByIdQuery;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CreateDietResource;
import org.upc.fitwise.plan.interfaces.rest.resources.UpdateDietResource;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CreateDietCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.UpdateDietCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.DietResourceFromEntityAssembler;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Diets Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to diets.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/diets</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/diets", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diets", description = "Diets Management Endpoints")
public class DietsController {
    private final DietQueryService dietQueryService;
    private final DietCommandService dietCommandService;



    public DietsController(DietQueryService dietQueryService,DietCommandService dietCommandService) {
        this.dietQueryService = dietQueryService;
        this.dietCommandService=dietCommandService;
    }

    /**
     * Gets all the diets.
     *
     * @return the list of all the diets resources
     * @see DietResource
     */
    @GetMapping
    public ResponseEntity<List<DietResource>> getAllDiets() {
        var getAllDietsQuery = new GetAllDietsQuery();
        var diets = dietQueryService.handle(getAllDietsQuery);
        var dietResource = diets.stream().map(DietResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(dietResource);
    }

    /**
     * Gets a diet by its id.
     *
     * @param dietId the id of the diet to be retrieved
     * @return the diet resource with the given id
     * @see DietResource
     */
    @GetMapping("/{dietId}")
    public ResponseEntity<DietResource> getDietById(@PathVariable Long dietId) {
        var getDietByIdQuery = new GetDietByIdQuery(dietId);
        var diet = dietQueryService.handle(getDietByIdQuery);
        if (diet.isEmpty()) return ResponseEntity.badRequest().build();
        var dietResource = DietResourceFromEntityAssembler.toResourceFromEntity(diet.get());
        return ResponseEntity.ok(dietResource);
    }


    /**
     * Creates a new diet.
     *
     * @param createDietResource the resource containing the data for the diet to be created
     * @return the created diet resource
     * @see CreateDietResource
     * @see DietResource
     */
    @PostMapping
    public ResponseEntity<DietResource> createDiet(@RequestBody CreateDietResource createDietResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var createDietCommand = CreateDietCommandFromResourceAssembler.toCommandFromResource(createDietResource,username);
        var dietId = dietCommandService.handle(createDietCommand);
        if (dietId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getDietByIdQuery = new GetDietByIdQuery(dietId);
        var diet = dietQueryService.handle(getDietByIdQuery);
        if (diet.isEmpty()) return ResponseEntity.badRequest().build();
        var DietResource = DietResourceFromEntityAssembler.toResourceFromEntity(diet.get());
        return new ResponseEntity<>(DietResource, HttpStatus.CREATED);
    }

    /**
     * Updates a diet.
     *
     * @param dietId the id of the diet to be updated
     * @param updateDietResource the resource containing the data for the Diet to be updated
     * @return the updated Diet resource
     * @see UpdateDietResource
     * @see DietResource
     */
    @PutMapping("/{dietId}")
    public ResponseEntity<DietResource> updateDiet(@PathVariable Long dietId, @RequestBody UpdateDietResource updateDietResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var updateDietCommand = UpdateDietCommandFromResourceAssembler.toCommandFromResource(dietId, updateDietResource,username);
        var updatedDiet = dietCommandService.handle(updateDietCommand);
        if (updatedDiet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var dietResource = DietResourceFromEntityAssembler.toResourceFromEntity(updatedDiet.get());
        return ResponseEntity.ok(dietResource);
    }



}