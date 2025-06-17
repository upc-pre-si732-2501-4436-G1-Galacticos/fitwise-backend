package org.upc.fitwise.plan.application.internal.outboundservices.acl;


import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.interfaces.acl.ProfilesContextFacade;

import java.util.Collections;
import java.util.List;


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
    public List<String> fetchProfileActivityLevelTagsByProfileId(Long profileId) {
        var profileActivityLevelTagsByProfileId= profilesContextFacade.fetchProfileActivityLevelTagsByProfileId(profileId);
        if (profileActivityLevelTagsByProfileId.isEmpty()) return Collections.emptyList();
        return profileActivityLevelTagsByProfileId;
    }



    /**
     * Fetch fetchProfileGoalNameByUserId by profileId
     *
     * @param profileId the profile to search for
     * @return profileGoalName if found, empty otherwise
     */
    public List<String> fetchProfileGoalTagsByProfileId(Long profileId) {
        var profileGoalTagsByProfileId= profilesContextFacade.fetchProfileGoalTagsByProfileId(profileId);
        if (profileGoalTagsByProfileId.isEmpty()) return Collections.emptyList();
        return profileGoalTagsByProfileId;
    }

}