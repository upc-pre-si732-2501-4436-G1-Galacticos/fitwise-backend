package org.upc.fitwise.profiles.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.profiles.domain.services.ProfileCommandService;
import org.upc.fitwise.profiles.domain.services.ProfileQueryService;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Profile Controller.
 * <p>
 * This class is the entry point for all the REST API calls related to profiles.
 * It is responsible for handling the requests and delegating the processing to the appropriate services.
 * It also transforms the data from the request to the appropriate commands and vice versa.
 * <ul>
 *     <li>GET /api/v1/profiles</li>
 * </ul>
 * </p>
 *
 *
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profiles Management Endpoints")
public class ProfilesController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;


    public ProfilesController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }









}