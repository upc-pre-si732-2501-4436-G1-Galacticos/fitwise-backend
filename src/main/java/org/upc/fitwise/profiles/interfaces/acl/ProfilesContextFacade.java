package org.upc.fitwise.profiles.interfaces.acl;

import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByUserIdQuery;
import org.upc.fitwise.profiles.domain.services.ProfileCommandService;
import org.upc.fitwise.profiles.domain.services.ProfileQueryService;

/**
 * Service Facade for the Profile context.
 *
 * <p>
 * It is used by the other contexts to interact with the Profile context.
 * It is implemented as part of an anti-corruption layer (ACL) to be consumed by other contexts.
 * </p>
 *
 */
@Service
public class ProfilesContextFacade {
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacade(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    /**
     * Fetches the name activity level of profile  by profileId
     *
     * @param profileId the profileId
     * @return the profile activity level name
     */
    public String fetchProfileActivityLevelNameByProfileId(Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return "";
        return profile.get().getActivityLevel().getLevelName();
    }
    /**
     * Fetches the name of goal  of profile  by profileId
     *
     * @param profileId the profileId
     * @return the profile goal name
     */
    public String fetchProfileGoalNameByProfileId(Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return "";
        return profile.get().getGoal().getGoalName();
    }


}