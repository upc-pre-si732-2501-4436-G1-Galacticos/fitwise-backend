package org.upc.fitwise.plan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsQuery;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
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
@Tag(name = "Diets ", description = "Diets Management Endpoints")
public class DietsController {
    private final DietQueryService dietQueryService;



    public DietsController(DietQueryService dietQueryService) {
        this.dietQueryService = dietQueryService;
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



}