package org.upc.fitwise.profiles.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByUserIdQuery;
import org.upc.fitwise.profiles.domain.services.ProfileCommandService;
import org.upc.fitwise.profiles.domain.services.ProfileQueryService;
import org.upc.fitwise.profiles.interfaces.rest.resources.CreateProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.resources.ProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.resources.UpdateProfileResource;
import org.upc.fitwise.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import org.upc.fitwise.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import org.upc.fitwise.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserController
 *
 * <p>Controller that handles the endpoints for users.
 * It uses the {@link ProfileQueryService} to handle the queries
 * for profile.
 * <ul>
 *     <li>GET /api/v1/users/{userId}/profile</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/users/{userId}/profile", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserProfileController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;


    public UserProfileController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    /**
     * GET /api/v1/users/{userId}/profile
     *
     * <p>Endpoint that returns the profile for a user</p>
     *
     * @param userId the user  ID
     * @return the profile for the user
     * @see ProfileResource
     */
    @GetMapping()
    public ResponseEntity<ProfileResource> getProfileForUserWithUserId(@PathVariable Long userId) {
        var getProfileByUserIdQuery = new GetProfileByUserIdQuery(userId);
        var profile = profileQueryService.handle(getProfileByUserIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }


    /**
     * POST /api/v1/users/{userId}/profile
     *  * <p>Endpoint that returns the profile for a user</p>
     * @param createProfileResource the resource containing the data for the profile to be created
     * @return the created profile resource
     * @see CreateProfileResource
     * @see ProfileResource
     */
    @PostMapping()
    public ResponseEntity<ProfileResource> createProfile(@PathVariable Long userId,@RequestBody CreateProfileResource createProfileResource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(userId,createProfileResource);
        var profileId = profileCommandService.handle(createProfileCommand);
        if (profileId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }


    /**
     * Updates a profile.
     *
     * @param userId             the id of the Profile to be updated
     * @param updateProfileResource the resource containing the data for the Profile to be updated
     * @return the updated Profile resource
     * @see UpdateProfileResource
     * @see ProfileResource
     */
    @PutMapping()
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long userId, @RequestBody UpdateProfileResource updateProfileResource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(userId, updateProfileResource);
        var updatedProfile = profileCommandService.handle(updateProfileCommand);
        if (updatedProfile.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(updatedProfile.get());
        return ResponseEntity.ok(profileResource);
    }

}