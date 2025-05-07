package org.upc.fitwise.iam.interfaces.rest.transform;

import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), token);
    }
}
