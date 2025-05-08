package org.upc.fitwise.profiles.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.domain.services.ProfileCommandService;
import org.upc.fitwise.profiles.domain.services.ProfileQueryService;
import org.upc.fitwise.profiles.interfaces.rest.resources.CreateProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.resources.ProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.resources.UpdateProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import org.upc.fitwise.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import org.upc.fitwise.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;

import java.util.List;

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