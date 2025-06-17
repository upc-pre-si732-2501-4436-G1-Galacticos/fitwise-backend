package org.upc.fitwise.profiles.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.profiles.domain.model.queries.GetAllGoalsQuery;
import org.upc.fitwise.profiles.domain.services.GoalQueryService;
import org.upc.fitwise.profiles.interfaces.rest.resources.GoalResource;
import org.upc.fitwise.profiles.interfaces.rest.transform.GoalResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Goals Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to goals.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/goals</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/goals", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Goals", description = "Goals Management Endpoints")
public class GoalsController {
    private final GoalQueryService goalQueryService;


    public GoalsController(GoalQueryService goalQueryService) {
        this.goalQueryService = goalQueryService;
    }


    /**
     * Gets all the goals.
     *
     * @return the list of all the goal resources
     * @see GoalResource
     */
    @GetMapping
    public ResponseEntity<List<GoalResource>> getAllGoals() {
        var getAllGoalsQuery = new GetAllGoalsQuery();
        var goals = goalQueryService.handle(getAllGoalsQuery);
        var goalResources = goals.stream().map(GoalResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(goalResources);
    }

}