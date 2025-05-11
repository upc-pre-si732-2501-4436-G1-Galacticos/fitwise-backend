package org.upc.fitwise.plan.application.internal.outboundservices.acl;


import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.interfaces.acl.ProfilesContextFacade;


/**
 * ExternalProfileService
 *
 * <p>
 *     This class is an outbound service used by the Learning Context to interact with the Profiles Context.
 *     It is implemented as part of an anti-corruption layer (ACL) to decouple the Learning Context from the Profiles Context.
 * </p>
 *
 */
@Service
public class ExternalProfileService {

    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    /**
     * Fetch ProfileActivityLevelName by profileId
     *
     * @param profileId the userId to search for
     * @return profileActivityLevelName if found, empty otherwise
     */
    public String fetchProfileActivityLevelNameByProfileId(Long profileId) {
        var profileActivityLevelName= profilesContextFacade.fetchProfileActivityLevelNameByProfileId(profileId);
        if (profileActivityLevelName.isEmpty()) return "";
        return profileActivityLevelName;
    }

    /**
     * Fetch fetchProfileGoalNameByUserId by profileId
     *
     * @param profileId the profile to search for
     * @return profileGoalName if found, empty otherwise
     */
    public String fetchProfileGoalNameByProfileId(Long profileId) {
        var profileGoalName= profilesContextFacade.fetchProfileGoalNameByProfileId(profileId);
        if (profileGoalName.isEmpty()) return "";
        return profileGoalName;
    }

}