package org.upc.fitwise.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.profiles.domain.model.queries.GetAllActivityLevelsQuery;
import org.upc.fitwise.profiles.domain.services.ActivityLevelQueryService;
import org.upc.fitwise.profiles.interfaces.rest.resources.ActivityLevelResource;
import org.upc.fitwise.profiles.interfaces.rest.transform.ActivityLevelResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ActivityLevels Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to ActivityLevels.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *  *     <li>GET /api/v1/activity-level</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/activity-levels", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Activity levels", description = "Activity levels Management Endpoints")
public class ActivityLevelsController {
    private final ActivityLevelQueryService activityLevelService;


    public ActivityLevelsController( ActivityLevelQueryService activityLevelyService) {
        this.activityLevelService = activityLevelyService;
    }


    /**
     * Gets all the ActivityLevels.
     *
     * @return the list of all the activity levels resources
     * @see ActivityLevelResource
     */
    @GetMapping
    public ResponseEntity<List<ActivityLevelResource>>getAllActivityLevel() {
        var getAllActivityLevelsQuery = new GetAllActivityLevelsQuery();
        var activityLevels = activityLevelService.handle(getAllActivityLevelsQuery);
        var activityLevelResources = activityLevels.stream().map(ActivityLevelResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(activityLevelResources);
    }


}