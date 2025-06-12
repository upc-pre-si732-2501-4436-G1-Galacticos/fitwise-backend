package org.upc.fitwise.plan.application.internal.outboundservices.acl;


import org.springframework.stereotype.Service;
import org.upc.fitwise.iam.domain.model.queries.GetUserByEmailQuery;
import org.upc.fitwise.iam.interfaces.acl.IamContextFacade;

import java.util.Optional;


/**
 * ExternalIamService
 *
 * <p>
 *     This class is an outbound service used by the Learning Context to interact with the Iam Context.
 *     It is implemented as part of an anti-corruption layer (ACL) to decouple the plan Context from the iam Context.
 * </p>
 *
 */
@Service
public class ExternalIamService {

    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Fetches the id of the user with the given email.
     * @param email The email of the user.
     * @return The id of the user.
     */
    public Optional<Long> fetchUserIdByEmail(String email) {
        var userId = iamContextFacade.fetchUserIdByEmail(email);
        if (userId == 0L) return Optional.empty();
        return Optional.of(userId);
    }

}