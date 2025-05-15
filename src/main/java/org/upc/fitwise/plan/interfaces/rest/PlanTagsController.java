package org.upc.fitwise.plan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsQuery;
import org.upc.fitwise.plan.domain.model.queries.GetAllPlanTagsQuery;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.domain.services.PlanTagQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
import org.upc.fitwise.plan.interfaces.rest.resources.PlanTagResource;
import org.upc.fitwise.plan.interfaces.rest.transform.DietResourceFromEntityAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.PlanTagResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Plan tags Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to plan tags.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/plan-tags</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/plan-tags", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plan tags ", description = "Plan tags Management Endpoints")
public class PlanTagsController {
    private final PlanTagQueryService planTagQueryService;



    public PlanTagsController(PlanTagQueryService planTagQueryService) {
        this.planTagQueryService = planTagQueryService;
    }


    /**
     * Gets all the Plan tags.
     *
     * @return the list of all the plan tags resources
     * @see PlanTagResource
     */
    @GetMapping
    public ResponseEntity<List<PlanTagResource>> getAllPlanTags() {
        var getAllPlanTagsQuery = new GetAllPlanTagsQuery();
        var planTags = planTagQueryService.handle(getAllPlanTagsQuery);
        var planTagResource = planTags.stream().map(PlanTagResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(planTagResource);
    }



}